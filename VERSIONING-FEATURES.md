# Features

Features listed below are used for versioning. See more details at used versioning pattern: 
[AO-SemVer](https://github.com/alcheware/alpha-oriented-semantic-versioning)

## f1: Allow auiditing for entities

1. Write "AuditableEntity" to be extended by all auditable entities

## f2: Represent an individual

1. Write "User" to be extended by all individual types.  
2. Write "Buyer" to represent a customer.  
3. Write "Seller" to represent a any kind of seller.  
4. Write "SellerAuthority" to represent the roles a seller can have.

## f3: Represent a store

1. A store must have a "LegalEntity", thus write "LegalEntity" to represent a legal entity.  
2. Write "Store" to represent stores.  
3. Write a StoreType enum to represent the type of store. Currently of no use, but for future scalability.    
4. A store must have Sellers to manage it. (bidirectional one to many)

## f4: Represent an address

1. Write Address class, it can be owned => unidirectional relationship.    
2. Represent a collection of addresses with "AddressCollection" class.  
3. A buyer must have an "AddressCollection".  => currently unidirectional relationship to allow AddressCollection to be used by any entity.  
4. A store must have an "Address".  
5. A legal entity must have an "Address"    
6. Change address collection to be a list of addresses. Still address is not ownership of any relationships it has. But no more middle class anymore.

## f5: Represent a review

1. Write "Rating" to be used by "Review" to represent a rating.    
2. Write "Review" to represent a review.  
+3. Reviews should be owned by the store, as well.

## f6: Updatable store rating

1. Store rating should be updatable without going through the entire review list and rating again.

## f7: Fundamental requests should be processable

1. Buyer controller is written.  
2. Buyer service is written.  
3. Buyer repository is written.  
4. Buyer Response Dto(s) are written.  
5. Buyer Request Dto(s) are written.  
6. Buyer dto mapper(s) are written.

7. Store controller is written.  
8. Store service is written.  
9. Store repository is written.  
10. Store Response Dto(s) are written.  
11. Store Request Dto(s) are written.  
12. Store dto mapper(s) are written.

13. Review controller is written.  
14. Review service is written.  
15. Review repository is written.  
16. Review Response Dto(s) are written.  
17. Review Request Dto(s) are written.

18. Address controller is written.    
19. Address service is written.    
20. Address repository is written.      
21. Address Response Dto(s) are written.    
22. Address Request Dto(s) are written.  
23. Address coordinates retrieveing api is written.

24. On update requests, AuditableEntity fields should persist.

## f8: Services retrieve only active entities

1. Write a general specificiation to be used accross all services that will retrieve only active entities (An entity that is not deleted and is enabled.)  
2. Use the specification in all services.  
3. Services should be scalable to allow custom specifications to be used.

## f9: Errors are handled

1. Write custom exceptions that provides detailed information.  
2. Write a global exception handler, capture all custom exceptions and return a detailed error message.

## f10: Structured responses

1. Write a response structure.  
2. Write a response builder to build the response structure.  
3. Write a Tuple record that stores same type of two objects. Tuple should have a mapped function to map the objects to a new object. Main use scenario is update requests.

## f11: Retrieve store listing in a recommended order

1. Write a spatialRecommendation engine to retrieve store listing in a recommended order.
2. Write a spatialRecommendation controller to handle requests to retrieve store listing in a recommended order.

## f12: Store details persists between a relational database and Solr

1. Write an indexing engine to enable indexing operations on Solr.
2. Better and patch-oriented store service (controller etc.).
3. Store update requests also update Solr index.

## f13: Logging

1. Prepare logging configurations.
2. Insert logging statements to certain parts of the code.

## f14: Swagger Documentation

1. Add fundamental swagger documentation

## f15: Dockerised Deployment

1. Dockerise the application

## f16: Unit & Testing

1. Write unit tests.
2. Write integration tests.

## f17: Controllers are validated

1. Write validation annotations to controllers.

## f18: Swagger Documentation

1. Add more detailed swagger documentation.

# Features can be implemented after release

- Pageable Recommendation listing (thanks to modifiable query structure).
- Store rating average should be periodically calculated from scratch and updated in the store table.
- Extend api documentation