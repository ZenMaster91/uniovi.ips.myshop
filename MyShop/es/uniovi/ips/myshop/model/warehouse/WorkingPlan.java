package es.uniovi.ips.myshop.model.warehouse;

import java.util.List;

import es.uniovi.ips.myshop.model.order.OrderDetail;
import es.uniovi.ips.myshop.model.order.Order;
import es.uniovi.ips.myshop.model.order.Order.Status;
import es.uniovi.ips.myshop.model.people.WharehouseKeeper;
import es.uniovi.ips.myshop.model.product.Product;

public class WorkingPlan {
	
	private WharehouseKeeper almacenero;
	private Order pedido;
	
	public WorkingPlan(Order pedido, WharehouseKeeper almacenero) {
		this.setAlmacenero(almacenero);
		this.pedido = pedido;
	}
	
	public List<Product> getOTShorted() {
		return null;
	}
	
	public boolean recogerProducto(String id) {
		for(OrderDetail dp : pedido.getProductos()) {
			if(dp.getProducto().getIDProducto()==id) {
				dp.recogido = true;
				return true;
			}
		}
		return false;
	}
	
	public boolean marcarParaEmpaquetado() {
		for(OrderDetail dp : pedido.getProductos()) {
			if(!dp.recogido)
				return false;
			if(!dp.incidencia.solved) {
				pedido.setEstado(Status.INCIDENCIA);
				return false;
			}
		}
		pedido.setEstado(Status.EMPAQUETANDO);
		return true;
	}
	
	public String getEtiquetaEnvio() {
		return null;
	}
	
	public String getAlbaranes() {
		return null;
	}

	public WharehouseKeeper getAlmacenero() {
		return almacenero;
	}

	public void setAlmacenero(WharehouseKeeper almacenero) {
		this.almacenero = almacenero;
	}

}