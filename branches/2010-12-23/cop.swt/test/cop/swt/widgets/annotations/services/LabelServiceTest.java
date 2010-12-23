package cop.swt.widgets.annotations.services;

import static cop.swt.widgets.annotations.services.LabelService.getAnnotatedItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.lang.reflect.Field;

import org.junit.Test;

import cop.swt.widgets.annotations.Label;

public class LabelServiceTest
{

//	@Test
//	public void testGetDescription()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetItemNameT()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetItemNameTString()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetItemNameTStringLocale()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
//	@Test
//	public void testGetItemClass()
//	{
//		fail("Not yet implemented"); // TODO
//	}
//
	@Test
	public void testGetAnnotatedItem() throws Exception
	{
		assertNull(getAnnotatedItem(null, null));
//		assertNull(getAnnotatedItem(new Integer(1).getClass(), null));
//		assertNull(getAnnotatedItem(int.class, null));
//		assertNull(getAnnotatedItem("test".getClass(), null));
		assertEquals(LabelFoo1.getField(), getAnnotatedItem(LabelFoo1.class, null));
		assertEquals(LabelFoo1.getField(), getAnnotatedItem(LabelFoo1.class, ""));
		assertEquals(LabelFoo1.getFieldModel1(), getAnnotatedItem(LabelFoo1.class, LabelFoo1.getFieldModelName1()));
		assertEquals(LabelFoo1.getFieldModel2(), getAnnotatedItem(LabelFoo1.class, LabelFoo1.getFieldModelName2()));
	}
//
//	@Test
//	public void testGetResultValue()
//	{
//		fail("Not yet implemented"); // TODO
//	}

}

final class LabelFoo1
{
	@Label
	String field;
	
	@Label(name = "model1")
	String fieldModel1;
	
	@Label(name = "model2")
	String fieldModel2;
	
	static Field getField() throws Exception
	{
		return LabelFoo1.class.getDeclaredField("field");
	}
	
	static Field getFieldModel1() throws Exception
	{
		return LabelFoo1.class.getDeclaredField("fieldModel1");
	}
	
	static String getFieldModelName1()
	{
		return "model1";
	}
	
	static Field getFieldModel2() throws Exception
	{
		return LabelFoo1.class.getDeclaredField("fieldModel2");
	}
	
	static String getFieldModelName2()
	{
		return "model2";
	}
}
