/**
* TestValida.java
* appEducacional
* 28/07/2014 18:06:57
* Copyright David Romero Alcaide
* com.app.pruebasunitarias.domainservices
*/
package com.app.pruebasunitarias.domainservices;

import org.junit.Test;
import org.springframework.util.Assert;

import com.app.domain.domainservices.Valida;


/**
 * @author David Romero Alcaide
 *
 */
public class TestValida {

	@Test
	public void testDNIOk()
	{
		Assert.isTrue(Valida.validaDNI("28842171X"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDNIFail()
	{
		Assert.isTrue(Valida.validaDNI("28442171X"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDNIFail2()
	{
		Assert.isTrue(Valida.validaDNI("test que falla"));
	}
	
	@Test()
	public void testDNIOk2()
	{
		Assert.isTrue(Valida.validaDNI("X28842171X"));
	}
	
}
