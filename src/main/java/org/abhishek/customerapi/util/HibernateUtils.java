package org.abhishek.customerapi.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtils {
	
	private static StandardServiceRegistry standardServiceRegistry;
	private static SessionFactory sessionFactory;
	
	static {
		if(sessionFactory == null) {
			System.out.println("HibernateUtils static block invoked for creation of sessionFactory");
			try {
				standardServiceRegistry = new StandardServiceRegistryBuilder()
						.configure()
						.build();
				MetadataSources metadataSources = new MetadataSources(standardServiceRegistry);
				Metadata metadata = metadataSources.getMetadataBuilder().build();
				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch(Exception e) {
				System.out.println("Exception happened during creation of Session Factory: " + e.getMessage());
				e.printStackTrace();
				if(standardServiceRegistry != null) {
					StandardServiceRegistryBuilder.destroy(standardServiceRegistry);
				}
				throw new IllegalStateException("Session Factory could not be initialized", e);
			}
			
		}
	}
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
