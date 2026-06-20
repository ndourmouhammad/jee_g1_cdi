### Guide : Injection de Dépendances
**Objectif** : Mettre en place une architecture propre pour la gestion d'une base de données en utilisant JPA pour la persistance et CDI pour la gestion du cycle de vie des objets.

---

### 1. Configuration de l'environnement (Le socle)

Avant de manipuler les objets, nous devons définir comment l'application communique avec la base de données et comment le conteneur d'injection doit se comporter.

#### 📄 `src/main/resources/META-INF/persistence.xml`
C'est le fichier de configuration de JPA. Il définit l'Unité de Persistance (PU).

```xml
<persistence xmlns="http://xmlns.jcp.org/xml/ns/persistence" version="2.1">
    <persistence-unit name="PERSISTENCE" transaction-type="RESOURCE_LOCAL">
        <properties>
            <!-- Connexion JDBC (PostgreSQL) -->
            <property name="javax.persistence.jdbc.driver" value="org.postgresql.Driver" />
            <property name="javax.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/jee_g1_assurance" />
            <property name="javax.persistence.jdbc.user" value="postgres" />
            <property name="javax.persistence.jdbc.password" value="root" />

            <!-- Configuration Hibernate -->
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect" />
            <property name="hibernate.show_sql" value="true" />
            <property name="hibernate.hbm2ddl.auto" value="update" />
        </properties>
    </persistence-unit>
</persistence>
```

#### 📄 `src/main/resources/META-INF/beans.xml`
Ce fichier est crucial pour activer CDI. Sans lui, les annotations `@Inject` seront ignorées.
```xml
<beans xmlns="https://jakarta.ee/xml/ns/jakartaee" bean-discovery-mode="all" version="4.0">
</beans>
```

---

### 2. Gestionnaire de Ressources : `JPAUtil` (Le Cœur)

Le rôle de cette classe est de "produire" des instances de `EntityManager` et de gérer leur fermeture. C'est ici que la magie de CDI opère.

#### 📄 `src/main/java/sn/gl/gestion_gl_g2_2026/utils/JPAUtil.java`
```java
@ApplicationScoped
public class JPAUtil {
    private EntityManagerFactory factory;

    @PostConstruct
    public void init() {
        factory = Persistence.createEntityManagerFactory("PERSISTENCE");
    }

    @Produces
    @RequestScoped
    public EntityManager createEntityManager() {
        return factory.createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager entityManager) {
        if (entityManager.isOpen()) {
            entityManager.close();
        }
    }

    @PreDestroy
    public void shutdown() {
        if (factory != null && factory.isOpen()) factory.close();
    }
}
```

#### 🔍 Explications des Annotations :

| Annotation | Rôle | Comment ça marche ? | Intérêt |
| :--- | :--- | :--- | :--- |
| **`@ApplicationScoped`** | Portée Application | L'instance de `JPAUtil` est créée une seule fois pour toute la durée de vie de l'application. | Évite de recréer la `EntityManagerFactory` qui est une opération très lourde. |
| **`@PostConstruct`** | Initialisation | S'exécute automatiquement après que le constructeur a fini son travail. | Garantit que la factory est prête avant toute utilisation. |
| **`@Produces`** | Producteur | Dit à CDI : "Si quelqu'un a besoin d'un `EntityManager`, utilise cette méthode pour lui en donner un". | Permet d'injecter des objets que l'on ne contrôle pas directement (comme ceux de JPA). |
| **`@RequestScoped`** | Portée Requête | L'objet produit ne vivra que le temps d'une seule requête HTTP. | Chaque utilisateur a son propre `EntityManager`, évitant les conflits de données et les erreurs "closed". |
| **`@Disposes`** | Nettoyage | CDI appelle cette méthode automatiquement à la fin de la portée (ici, à la fin de la requête HTTP). | **Crucial** : Empêche les fuites de mémoire et ferme proprement la connexion à la base de données. |
| **`@PreDestroy`** | Destruction | S'exécute juste avant que l'application ne s'arrête. | Ferme la factory proprement. |

---

### 3. La Couche Repository (Accès aux données)

Grâce au producteur défini précédemment, nous pouvons maintenant injecter l' `EntityManager` partout où nous en avons besoin.

#### 📄 `src/main/java/sn/gl/gestion_gl_g2_2026/repository/impl/AssuranceRepository.java`
```java
@ApplicationScoped
public class AssuranceRepository implements ICrud<Assurance> {

    @Inject
    private EntityManager entityManager;

    @Override
    public List<Assurance> getAll() {
        return entityManager.createQuery("FROM Assurance", Assurance.class).getResultList();
    }

    @Override
    public void insert(Assurance assurance) {
        entityManager.getTransaction().begin();
        entityManager.persist(assurance);
        entityManager.getTransaction().commit();
    }
    // ... autres méthodes (update, delete, get)
}
```

#### 🔍 Pourquoi cette approche ?
- **`@Inject`** : Le Repository ne sait pas comment l' `EntityManager` est créé, il demande juste à CDI de lui fournir. C'est le principe de l'inversion de contrôle (IoC).
- **Découplage** : Si demain vous changez de base de données ou de méthode de création, vous ne modifiez que `JPAUtil`, pas vos 50 repositories.

---

### 4. La Couche Contrôleur (Servlet)

Enfin, nous utilisons CDI pour injecter nos Repositories dans les Servlets.

#### 📄 `src/main/java/sn/gl/gestion_gl_g2_2026/controller/TypeServlet.java`
```java
@WebServlet(name = "type", value = "/type")
@RequestScoped
public class TypeServlet extends HttpServlet {

    @Inject
    private ICrud<TypeAssurance> typeRepository;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<TypeAssurance> list = this.typeRepository.getAll();
        req.setAttribute("types", list);
        req.getRequestDispatcher("/type/list.jsp").forward(req, resp);
    }
}
```

---

### 💡 Synthèse pour la Démonstration

**Le problème classique** : Souvent, on crée un `EntityManager` manuellement. Si on oublie de le fermer, le serveur sature. Si on le ferme trop tôt, on a l'erreur `EntityManager is closed`.

**La Solution "Propre" (CDI + JPA)** :
1. **Automatisation** : On délègue la création et la destruction à CDI.
2. **Cycle de vie maîtrisé** : L' `EntityManager` naît au début de la requête HTTP et meurt à la fin grâce à `@RequestScoped` et `@Disposes`.
3. **Lisibilité** : Le code métier (Repositories) est débarrassé de la plomberie technique (ouverture/fermeture de sessions).