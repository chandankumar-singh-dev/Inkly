# Inkly

> **Write. Discover. Connect.**

Inkly is a modern blogging and content-sharing platform where users can publish rich articles containing text, photos, and videos, while other users can discover, read, interact with, and follow content creators.

This project is being developed as a **learning-focused production-style application** using **Spring Boot for the backend** and **Flutter for the mobile application**.

The primary goal is not only to build a working application, but to understand how to design and develop a real-world application from **requirements → database design → backend architecture → REST APIs → mobile application**.

---

## 🚧 Project Status

**Current Version: V1 — Requirements & Design**

The V1 requirements have been defined.

Database design and implementation are the next development phases.

### Development Roadmap

* [x] Define product vision
* [x] Define V1 requirements
* [ ] Identify domain entities
* [ ] Define relationships
* [ ] Design ER diagram
* [ ] Design relational database
* [ ] Implement database schema
* [ ] Implement Spring Boot backend
* [ ] Implement authentication & authorization
* [ ] Implement REST APIs
* [ ] Implement Flutter application
* [ ] Integrate Flutter with backend
* [ ] Testing
* [ ] Dockerization
* [ ] CI/CD
* [ ] Deployment

---

# 1. Product Vision

The goal of Inkly is to provide a simple platform where:

> Anyone can discover and read content without authentication, while registered users can create content and participate in the community.

The platform separates **content consumption** from **user participation**.

A guest should be able to open the application and immediately start discovering and reading content without being forced to create an account.

Authentication becomes necessary when a user wants to participate in the platform.

---

# 2. Core User Types

Inkly has two primary user states.

## Guest User

A guest can:

* Browse blogs
* Search blogs
* Read blogs
* View photos and videos
* Contribute to blog read statistics
* Maintain reading history

A guest cannot:

* Create blogs
* Like blogs
* Comment
* Reply to comments
* Like comments
* Follow users
* Bookmark blogs
* Manage content

---

## Authenticated User

An authenticated user can perform everything a guest can do, plus:

### Content

* Create a blog
* Save a blog as a draft
* Publish a blog
* Edit their own blog
* Delete their own blog

### Engagement

* Like/unlike a blog
* Comment on a blog
* Edit their own comment
* Delete their own comment
* Reply to comments
* Like/unlike comments

### Social

* Follow/unfollow users

### Personal

* Bookmark/unbookmark blogs
* View bookmarks
* View reading history

---

# 3. Blog & Content

A blog is the primary content unit of Inkly.

A published blog can be discovered and read by other users.

A blog contains:

* Title
* Rich article content
* Photos
* Videos
* Tags
* Publication state

The author can keep a blog as a **draft** before publishing it.

### Blog lifecycle

```text
Create
   ↓
Draft
   ↓
Edit
   ↓
Publish
   ↓
Published
   ↓
Edit / Delete
```

Only published blogs are available for normal public discovery.

---

# 4. Rich Content

Inkly should support media inside the article rather than restricting images and videos to a separate gallery.

Example:

```text
Title

Paragraph

[Photo]

Paragraph

[Video]

Heading

Paragraph

[Photo]

Paragraph
```

The author should be able to control where media appears within the article.

This requirement will influence the eventual content and database design.

---

# 5. Discovery

Guests and authenticated users should be able to discover content.

## Home / Discovery

Guests can see:

* Popular posts
* Recommended posts

Authenticated users see a mixture of:

* Posts from users they follow
* Popular posts
* Recommended posts

The initial version intentionally uses a **simple feed strategy** rather than a complex recommendation algorithm.

---

# 6. Following

Users can follow other users.

Following someone does not mean displaying all of that person's historical posts in the home feed.

Instead, the feed should prioritize appropriate recent/new content from followed users while continuing to include popular or recommended content.

Older posts remain discoverable through:

* User profiles
* Search
* Other discovery mechanisms

---

# 7. Profiles

## Public Profile

When viewing another user's profile, users can see:

* Name
* Username
* Followers
* Following
* Published posts

Private information such as drafts, bookmarks, and reading history is not publicly displayed.

## Own Profile

A user can additionally access:

* Draft posts
* Bookmarked posts
* Reading history

---

# 8. Comments

Authenticated users can participate in discussions.

A user can:

* Comment on a blog
* Edit their own comment
* Delete their own comment
* Reply to another comment
* Like/unlike comments

Example:

```text
Blog
│
├── Comment
│    ├── Reply
│    ├── Reply
│    └── Like
│
└── Comment
     └── Reply
```

---

# 9. Likes

Authenticated users can:

* Like a blog
* Remove their like
* Like a comment
* Remove their like

A user should not be able to create multiple likes for the same target.

---

# 10. Bookmarks

Authenticated users can save blogs for later.

Bookmarks are private.

A user's bookmarks are not visible on their public profile.

---

# 11. Reading History & Read Statistics

Reading is available without authentication.

Inkly maintains two separate concepts:

### Read Statistics

A blog can have a global read count.

Example:

```text
Spring Boot REST API
👁 1,245 reads
```

### Reading History

The application can remember content that an individual user/device has read.

Reading history is private.

This distinction allows Inkly to answer two different questions:

> How many times has this content been read?

and

> What content has this user/device already read?

---

# 12. Authentication

V1 authentication uses:

**Email + Password**

Authentication is not required for reading content.

Authentication is required for actions that represent a user's identity or personal state.

### V1

* Registration
* Login
* Logout
* Protected user actions

### Future

Authentication providers may later include:

* Google
* Other OAuth providers

These are intentionally outside the initial V1 scope.

---

# 13. Technology Stack

## Backend

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Bean Validation
* Relational Database
* REST API

The exact database technology will be finalized during the database-design phase.

---

## Mobile

* Flutter
* Dart
* REST API integration

---

## Development Tools

* Git
* GitHub
* drawDB for database/ERD design
* Postman or equivalent API testing tool

Additional tools will be introduced as the project evolves.

---

# 14. Architecture

The backend will follow a layered architecture.

```text
Flutter Mobile App
        │
        │ HTTP / JSON
        ▼
   REST Controllers
        │
        ▼
      Services
        │
        ▼
    Repositories
        │
        ▼
     Database
```

The project will avoid putting business logic directly inside controllers.

---

# 15. Repository Structure

The project will eventually be organized approximately as:

```text
inkly/
│
├── README.md
├── LICENSE
├── .gitignore
│
├── docs/
│   ├── requirements/
│   ├── database/
│   ├── architecture/
│   └── api/
│
├── inkly-backend/
│   └── ...
│
└── inkly-mobile/
    └── ...
```

Documentation will evolve together with the application.

---

# 16. Development Philosophy

This project is intentionally being developed incrementally.

The development process is:

```text
Requirements
     ↓
Domain Understanding
     ↓
Entity Identification
     ↓
Relationship Analysis
     ↓
ER Diagram
     ↓
Database Design
     ↓
Backend Architecture
     ↓
API Design
     ↓
Implementation
     ↓
Flutter UI
     ↓
Integration
     ↓
Testing
     ↓
Deployment
```

The database will **not** be designed by blindly creating tables from feature names.

Instead, the domain will first be analyzed to understand:

* What information must be stored?
* Why does that information exist?
* How is information related?
* What rules apply?
* What constraints are required?

---

# 17. V1 Scope

The first version focuses on the core blogging experience:

### Reading

* Public content discovery
* Search
* Blog reading
* Media viewing
* Read statistics
* Reading history

### Publishing

* Create blog
* Draft
* Publish
* Edit
* Delete
* Rich content
* Photos
* Videos
* Tags

### Social

* Follow users
* Like posts
* Comment
* Reply
* Like comments
* Bookmark posts

### Account

* Registration
* Login
* Logout
* Profile

---

# 18. Out of Scope for V1

The following features are intentionally postponed:

* Google authentication
* Advanced recommendation algorithms
* Notifications
* Real-time chat
* Monetization
* Advertising
* Multiple authors per post
* Collaborative editing
* Scheduled publishing
* Content version history
* Advanced moderation system
* Analytics dashboard
* Admin panel

These may be introduced in future versions.

---

# 19. Future Roadmap

### V1

Core blogging platform.

### V2

Potential improvements:

* Google authentication
* Notifications
* Better recommendation system
* Advanced search
* Categories
* Improved media management

### V3

Potential platform features:

* Admin/moderation dashboard
* Analytics
* Content reporting
* Advanced personalization
* Production deployment and scaling

---

# 20. Learning Goals

This project is also a practical software-engineering learning exercise.

Key learning objectives include:

* Requirements analysis
* Domain modeling
* Relational database design
* Entity relationships
* Database normalization
* Spring Boot architecture
* REST API design
* Authentication and authorization
* JPA/Hibernate
* Flutter application architecture
* API integration
* Validation
* Error handling
* Testing
* Git/GitHub workflow
* Docker
* CI/CD
* Deployment

The project intentionally prioritizes understanding **why** design decisions are made instead of simply implementing predefined code.

---

## License

License will be added when the project reaches the implementation phase.
