# Inkly — V1 Requirements

> **Write. Discover. Connect.**

## 1. Document Information

| Field | Value |
|---|---|
| Project | Inkly |
| Version | V1 |
| Document Type | Product & Functional Requirements |
| Status | Draft |
| Backend | Spring Boot |
| Mobile App | Flutter |
| Authentication | Email + Password |

---

# 2. Product Overview

Inkly is a blogging and content-sharing platform where users can create, publish, discover, read, and interact with rich blog content.

A blog can contain text, photos, and videos. Users can interact with published content through likes and comments, follow other users, and save content for later.

The platform supports two primary experiences:

1. **Guest experience** — users can discover and read content without creating an account.
2. **Authenticated experience** — registered users can create content and participate in the community.

---

# 3. Product Goals

V1 aims to:

- Allow public reading without authentication.
- Allow registered users to publish rich content.
- Allow photos and videos to appear at different positions within an article.
- Support likes and comments.
- Support replies to comments.
- Support following users.
- Provide a simple personalized feed.
- Support bookmarks.
- Maintain reading history.
- Track reading statistics.
- Establish a foundation for future versions.

---

# 4. Guest User

A guest can:

- Browse published blogs.
- Search blogs.
- Search users.
- Open and read published blogs.
- View photos and videos.
- Contribute to read statistics.
- Maintain anonymous/local reading history.

A guest cannot:

- Create blogs.
- Save drafts.
- Publish blogs.
- Edit blogs.
- Delete blogs.
- Like blogs.
- Comment.
- Reply to comments.
- Like comments.
- Follow users.
- Bookmark blogs.

---

# 5. Authenticated User

An authenticated user can perform all guest actions plus:

### Content

- Create a blog.
- Save a draft.
- Publish a blog.
- Edit their own blog.
- Delete their own blog.

### Engagement

- Like/unlike a blog.
- Comment on a blog.
- Edit their own comment.
- Delete their own comment.
- Reply to comments.
- Like/unlike comments.

### Social

- Follow/unfollow users.

### Personal

- Bookmark/unbookmark blogs.
- View bookmarks.
- View drafts.
- View reading history.
- Manage their own profile.

---

# 6. Authentication

V1 uses email and password authentication.

## Registration

A new user can register using:

- Email
- Password

The email address must be unique.

## Login

A registered user can log in using email and password.

## Logout

An authenticated user can log out.

## Authentication Boundary

Authentication is not required for reading.

Authentication is required for operations that represent a user's identity or personal state.

---

# 7. User Profiles

## Public Profile

Other users can view:

- Name
- Username
- Follower count
- Following count
- Published posts

They cannot view:

- Draft posts
- Bookmarks
- Reading history

## Own Profile

The owner can additionally view:

- Draft posts
- Bookmarked posts
- Reading history

---

# 8. Blog

A blog contains:

- Title
- Rich article content
- Photos
- Videos
- Tags
- Publication state
- Author information

A blog is created by an authenticated user.

---

# 9. Blog Lifecycle

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

## Draft

A draft is private to its author.

## Published

A published blog is publicly discoverable and readable.

## Ownership

Only the author can edit or delete their own blog.

---

# 10. Rich Content

V1 should support common article formatting:

- Paragraphs
- Headings
- Bold text
- Italic text
- Bullet lists
- Numbered lists
- Links

The exact editor and content-storage format are technical design decisions.

---

# 11. Media

A blog can contain multiple photos and videos.

Media is not limited to a separate gallery.

The author can place media at different positions inside the article.

Example:

```text
Title

Paragraph

[PHOTO]

Paragraph

[VIDEO]

Heading

Paragraph

[PHOTO]

Paragraph
```

The order and position of content and media must be preserved when displayed.

V1 supports:

- Multiple photos
- Multiple videos
- Photos inside article content
- Videos inside article content

The actual storage mechanism is a technical decision and is not defined by this requirements document.

---

# 12. Reading

Published blogs can be read without authentication.

A reader can:

- Open a published blog.
- Read the article.
- View photos.
- View videos.
- See the author.
- See public interaction information.

Only published blogs are available for normal public reading.

---

# 13. Search

Guests and authenticated users can search published blogs.

Search should support relevant blog information such as:

- Title
- Content
- Tags

Guests and authenticated users can also search for users and open public profiles.

---

# 14. Blog Likes

Authenticated users can:

- Like a published blog.
- Unlike a blog.

A user must not have multiple active likes for the same blog.

The application should expose the total number of likes.

---

# 15. Comments

Authenticated users can comment on published blogs.

Users can:

- Create comments.
- Edit their own comments.
- Delete their own comments.
- Read comments from other users.

A user cannot modify another user's comment.

---

# 16. Comment Replies

Users can reply to comments.

Example:

```text
Comment
├── Reply
├── Reply
└── Reply
```

A reply is associated with the comment it responds to.

V1 focuses on simple threaded discussion rather than a separate messaging system.

---

# 17. Comment Likes

Authenticated users can:

- Like comments.
- Unlike comments.

A user must not have multiple active likes for the same comment.

The application should expose the total number of comment likes.

---

# 18. Following

Authenticated users can:

- Follow another user.
- Unfollow another user.
- View follower count.
- View following count.

The same user cannot be followed multiple times.

Self-following should not be allowed unless explicitly introduced in a future requirement.

---

# 19. Home Feed

The authenticated home page contains a simple mixture of:

1. Recent/new posts from followed users.
2. Popular/recommended posts.

V1 intentionally does not implement a complex recommendation algorithm.

Following a user does not insert all historical posts from that user into the feed.

Older posts remain discoverable through profiles, search, and other discovery mechanisms.

---

# 20. Popular / Recommended Content

Guests can see popular or recommended content.

Authenticated users see popular/recommended content alongside posts from followed users.

Potential popularity signals include:

- Reads
- Likes
- Comments

The exact formula is a later implementation decision.

---

# 21. Bookmarks

Authenticated users can:

- Bookmark published blogs.
- Remove bookmarks.
- View their bookmarks.

Bookmarks are private.

---

# 22. Reading History

Inkly supports reading history for guest and authenticated experiences.

There are two separate concepts:

### Read Statistics

Global statistics about content consumption.

Example:

```text
Spring Boot Tutorial
Reads: 1,245
```

### Personal Reading History

Content that an individual user/device has read.

Example:

```text
Reading History

- Spring Boot Tutorial
- Flutter State Management
- PostgreSQL Basics
```

Reading history is private.

---

# 23. Read Count

Opening/reading a published blog can contribute to its read count.

The system should maintain a total read statistic.

The exact rules for repeated reads, refreshes, and repeated access will be decided during technical design.

---

# 24. Privacy

Public:

- Name
- Username
- Followers
- Following
- Published posts

Private:

- Draft posts
- Bookmarks
- Reading history

A user must not be able to access another user's private information through normal application functionality.

---

# 25. Authorization

Authentication answers:

> Who is this user?

Authorization answers:

> Is this user allowed to perform this action?

Examples:

```text
User A
├── Edit own blog       → Allowed
├── Delete own blog     → Allowed
└── Edit User B's blog  → Denied
```

```text
User A
├── Edit own comment       → Allowed
├── Delete own comment     → Allowed
└── Edit User B's comment  → Denied
```

---

# 26. Core User Journeys

## Guest Reading

```text
Open App
   ↓
Home / Discovery
   ↓
Popular / Recommended
   ↓
Select Blog
   ↓
Read Blog
   ├── View Photos
   └── View Videos
```

## Guest Search

```text
Open App
   ↓
Search
   ↓
Enter Search Term
   ↓
Search Results
   ↓
Select Blog
   ↓
Read Blog
```

## Publishing

```text
Login
  ↓
Create Blog
  ↓
Write Content
  ├── Add Photo
  ├── Add Video
  └── Add Tags
  ↓
Save Draft
  ↓
Edit
  ↓
Publish
```

## Interaction

```text
Read Blog
├── Like
├── Comment
│    ├── Reply
│    └── Like Comment
└── Bookmark
```

## Following

```text
Search User
    ↓
Open Profile
    ↓
Follow User
    ↓
Future/recent posts
    ↓
Home Feed
```

---

# 27. Functional Requirements

| ID | Requirement | Guest | Authenticated |
|---|---|:---:|:---:|
| FR-01 | Browse published blogs | Yes | Yes |
| FR-02 | Search blogs | Yes | Yes |
| FR-03 | Search users | Yes | Yes |
| FR-04 | Read published blogs | Yes | Yes |
| FR-05 | View blog media | Yes | Yes |
| FR-06 | Track reading statistics | Yes | Yes |
| FR-07 | Maintain reading history | Yes | Yes |
| FR-08 | Register | Yes | No |
| FR-09 | Login | Yes | No |
| FR-10 | Logout | No | Yes |
| FR-11 | Create blog | No | Yes |
| FR-12 | Save draft | No | Yes |
| FR-13 | Publish blog | No | Yes |
| FR-14 | Edit own blog | No | Yes |
| FR-15 | Delete own blog | No | Yes |
| FR-16 | Like blog | No | Yes |
| FR-17 | Comment | No | Yes |
| FR-18 | Edit own comment | No | Yes |
| FR-19 | Delete own comment | No | Yes |
| FR-20 | Reply to comment | No | Yes |
| FR-21 | Like comment | No | Yes |
| FR-22 | Follow user | No | Yes |
| FR-23 | Bookmark blog | No | Yes |
| FR-24 | View bookmarks | No | Yes |
| FR-25 | View drafts | No | Yes |
| FR-26 | View own reading history | No | Yes |
| FR-27 | View public profile | Yes | Yes |
| FR-28 | Personalized home feed | No | Yes |

---

# 28. Non-Functional Requirements

## Security

- Passwords must never be stored as plain text.
- Protected operations require authentication.
- Authorization must be checked for protected resources.
- Private information must not be exposed through public APIs.

## Performance

- Blog lists should support pagination.
- Search results should support pagination.
- Home feed should support pagination.
- Large media files should not unnecessarily increase relational database size.
- APIs should return only required information.

## Maintainability

The backend should have clear separation between:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

## Scalability

The architecture should allow future features without major redesign.

---

# 29. V1 Out of Scope

- Google authentication
- Other OAuth providers
- Notifications
- Real-time chat
- Monetization
- Advertising
- Collaborative editing
- Multiple authors
- Scheduled publishing
- Version history
- Advanced recommendation algorithms
- Advanced moderation
- Admin dashboard
- Analytics dashboard
- Real-time notifications

---

# 30. V1 Success Criteria

V1 is functionally complete when:

1. Guests can open the app without authentication.
2. Guests can discover and search published blogs.
3. Guests can read blogs containing text, photos, and videos.
4. Reading statistics are tracked.
5. Reading history can be maintained.
6. Users can register and log in using email/password.
7. Users can create and manage their own blogs.
8. Blogs support media at different positions.
9. Users can like blogs.
10. Users can comment and reply.
11. Users can like comments.
12. Users can follow users.
13. Followed users influence the home feed.
14. Users can bookmark blogs.
15. Drafts and bookmarks remain private.
16. Users cannot modify another user's protected resources.
17. Flutter communicates successfully with the Spring Boot backend.

---

# 31. Next Phase

The next phase is **Domain & Database Design**.

We will not immediately create tables.

Instead we will ask:

> What information must Inkly remember to satisfy these requirements?

The process will be:

```text
Requirements
      ↓
Domain concepts
      ↓
Candidate entities
      ↓
Attributes
      ↓
Relationships
      ↓
Cardinality
      ↓
Normalization
      ↓
ER Diagram
      ↓
Database schema
```
