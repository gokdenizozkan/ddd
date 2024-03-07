# ddd

Ding Dong Delicious (DDD) is a web app built with Java Spring Boot
that brings local restaurants together with customers looking for a delicious meal.

## Table of Contents

- [ddd](#ddd)
    - [Table of Contents](#table-of-contents)
    - [Versioning](#versioning)
    - [Diagrams and Relationships](#diagrams-and-relationships)

## Versioning

This project uses [AO-SemVer](https://github.com/alcheware/alpha-oriented-semantic-versioning) to format committing,
which is an extended version of Semantic Versioning (SemVer).

### Features

#### ~~f1: Allow auiditing for entities~~

~~1. Write "AuditableEntity" to be extended by all auditable entities~~

#### f2: Represent an individual

~~1. Write "User" to be extended by all individual types.~~  
~~2. Write "Buyer" to represent a customer.~~  
3. Write "Seller" to represent a any kind of seller.  
4. Write "SellerAuthority" to represent the roles a seller can have.  

#### f3: Represent a store

1. A store must have a "LegalEntity", thus write "LegalEntity" to represent a legal entity.  
2. Write "Store" to be extended by all store types.  
3. Write "Restaurant" to represent a restaurant.  
4. A store must have Sellers to manage it. (bidirectional one to many)

#### f4: Represent an address

~~1. Write Address class, it can be owned => unidirectional relationship.~~    
~~2. Represent a collection of addresses with "AddressCollection" class.~~  
~~3. A buyer must have an "AddressCollection".  => currently unidirectional relationship to allow AddressCollection to be used by any entity.~~  
4. A store must have an "Address".  
5. A legal entity must have an "Address"  
~~6. Change address collection to be a list of addresses. Still address is not ownership of any relationships it has. But no more middle class anymore.~~

#### f5: Represent a review

1. Write "Rating" to be used by "Review" to represent a rating.  
2. Write "Review" to represent a review.  

## Diagrams and Relationships

```mermaid
---
title: Entity Relationships
---

classDiagram
    direction TB
    AuditableEntity <|-- User
    AuditableEntity <|-- Address
    AuditableEntity <|-- Store
    AuditableEntity <|-- LegalEntity
    AuditableEntity <|-- Review
    User <|-- Buyer
    User <|-- Seller
    Store <|-- Restaurant
    Address "1" --* "1" Store
    Address "1" --* "1" LegalEntity
    Address "*" --* "1" Buyer
    Store "*" *--* "1" LegalEntity
    Store "1" *--* "*" Seller
    SellerAuthority "*" --* "1" Seller  
    Review "*" *--* "1" Buyer
    Review "*" --* "1" Rating

    class AuditableEntity {
        <<abstract>>
        -LocalDateTime createdAt
        -LocalDateTime updatedAt
        -Boolean deleted
        -Boolean enabled
    }

    class User {
        <<abstract>>
        -String name
        -String surname
        -String email
        -String phone
        -LocalDate birthdate
    }

    class Buyer {
        -List~Address~ addresses
    }

    class Seller {
        -Store store
        -SellerAuthority authority
    }

    class SellerAuthority {
        <<enumeration>>
        +MANAGER $
    }

    class Store {
        <<abstract>>
        -String name
        -String email
        -String phone
        -LegalEntity legalEntity
        -Address address
        -List~Seller~ sellers
    }

    class Address {
        -BigDecimal latitude
        -BigDecimal longitude
        -String countryCode
        -String city
        -String region
        -String addressLine
        -String addressDetails
        -String contactPhone
    }

    class LegalEntity {
        -String name
        -String email
        -String phone
        -Address address
        -List~Store~ stores
    }

    class Review {
        -User customer
        -Rating rating
        -String experience
    }

    class Rating {
        <<enumeration>>
        +EXCELLENT
        +SATISFACTORY
        +DECENT
        +LACKING
        +POOR
        -Rating(Integer value)
        +Double normalized()
    }
```