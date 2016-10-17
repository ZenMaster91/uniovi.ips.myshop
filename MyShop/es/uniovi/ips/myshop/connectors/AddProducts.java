package es.uniovi.ips.myshop.connectors;

import java.sql.SQLException;

import es.uniovi.ips.myshop.database.InBoardConnector;
import es.uniovi.ips.myshop.model.product.Product;

/**
 * 
 * AddProducts.java Adds a product to the database.
 *
 * @author Guillermo Facundo Colunga
 * @version 0810161300
 * @since 8 de oct. de 2016
 * @formatter Oviedo Computing Community
 */
public class AddProducts extends InBoardConnector {

	@SuppressWarnings("unused")
	private Product product;
	
	/**
	 * Adds the given product to the database.
	 * 
	 * @param product to add to the database.
	 * @throws SQLException if there's any problem connecting to the database or
	 *             executing the query.
	 */
	public AddProducts(Product product) throws SQLException {
		this.product = product;
	}

	@Override
	protected void query() {
		// Not implemented yet.
	}

}
