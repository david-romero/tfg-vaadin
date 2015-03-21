/**
* BanearUsuarioView.java
* appEducacionalVaadin
* 21/3/2015 23:07:16
* Copyright David
* com.app.ui.user.admin.view.banear.usuarios
*/
package com.app.ui.user.admin.view.banear.usuarios;


/**
 * @author David
 *
 */
public class BanearUsuarioView  extends BanearUsuarioViewDesign{

	private com.app.ui.user.admin.presenter.AdminPresenter presenter;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7560486752156416103L;
	
	public BanearUsuarioView(){
		presenter = AdminPresenter.getInstance();
	}

}
