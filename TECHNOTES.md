**Some Tech Notes**
1. Usage of DTO to show limited data to the client.
I started using mapstruct library for conversion from model (entity) to dto.

2. Error
the type query is not generic it cannot be parameterized with arguments site:stackoverflow.com
Query<Customer> createQuery = session.createQuery(criteriaQuery);
I changed the import to import org.hibernate.query.Query; and it worked. 
Question: Why did it not work with javax.persistence import.

3. Building and invoking request using jersey rest client
https://dennis-xlc.gitbooks.io/restful-java-with-jax-rs-2-0-2rd-edition/content/en/part1/chapter8/building_and_invoking_requests.html

4. 