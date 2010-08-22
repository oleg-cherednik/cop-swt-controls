package cop.common.beans;

import static cop.common.beans.JavaBean.getPropertyName;
import static cop.common.beans.JavaBean.getPropertyNameByMethodName;
import static cop.common.beans.JavaBean.getSetterMethod;
import static cop.common.beans.JavaBean.getSetterMethodName;
import static cop.common.beans.JavaBean.getSetterMethodNameByGetterMethodName;
import static cop.common.beans.JavaBean.getSetterMethodNameByPropertyName;
import static cop.common.beans.JavaBean.isGetterMethod;
import static cop.common.beans.JavaBean.isGetterMethodName;
import static cop.common.beans.JavaBean.isSetterMethod;
import static cop.common.beans.JavaBean.isSetterMethodName;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Test;

public class JavaBeanTest
{
	private Class<?> obj = new Foo().getClass();

	@Test
	public void testGetPropertyName()
	{
		try
		{
			assertEquals("name", getPropertyName(obj.getDeclaredMethod("getName")));
			assertEquals("enabled", getPropertyName(obj.getDeclaredMethod("isEnabled")));
		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameException1()
	{
		getPropertyName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameException2() throws IllegalArgumentException, SecurityException,
	                NoSuchMethodException
	{
		getPropertyName(obj.getConstructor());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameException3() throws IllegalArgumentException, SecurityException,
	                NoSuchMethodException
	{
		getPropertyName(obj.getDeclaredMethod("get"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameException4() throws IllegalArgumentException, SecurityException,
	                NoSuchMethodException
	{
		getPropertyName(obj.getDeclaredMethod("set", String.class));
	}

	@Test
	public void testGetSetterMethodName()
	{
		try
		{
			assertEquals("setName", getSetterMethodName(obj.getDeclaredMethod("setName", String.class)));
			assertEquals("setName", getSetterMethodName(obj.getDeclaredMethod("getName")));
			assertEquals("setName", getSetterMethodName(obj.getDeclaredField("name")));
			assertEquals("setEnabled", getSetterMethodName(obj.getDeclaredMethod("setEnabled", boolean.class)));
			assertEquals("setEnabled", getSetterMethodName(obj.getDeclaredMethod("isEnabled")));
			assertEquals("setEnabled", getSetterMethodName(obj.getDeclaredField("enabled")));
		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameException1()
	{
		getSetterMethodName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameException2() throws SecurityException, NoSuchMethodException
	{
		getSetterMethodName(obj.getConstructor());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameException3() throws SecurityException, NoSuchMethodException
	{
		getSetterMethodName(obj.getDeclaredMethod("get"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameException4() throws SecurityException, NoSuchMethodException
	{
		getSetterMethodName(obj.getDeclaredMethod("set", String.class));
	}

	@Test
	public void testIsGetterMethodName()
	{
		try
		{
			assertTrue(isGetterMethodName(obj.getDeclaredMethod("getName").getName()));
			assertTrue(isGetterMethodName(obj.getDeclaredMethod("isEnabled").getName()));

			assertFalse(isGetterMethodName(obj.getDeclaredMethod("setName", String.class).getName()));
			assertFalse(isGetterMethodName(obj.getConstructor().getName()));
			assertFalse(isGetterMethodName(null));
			assertFalse(isGetterMethodName(""));
			assertFalse(isGetterMethodName(obj.getDeclaredMethod("get").getName()));
			assertFalse(isGetterMethodName(obj.getDeclaredMethod("set", String.class).getName()));
			assertFalse(isGetterMethodName("value"));

		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testIsGetterMethod()
	{
		try
		{
			assertTrue(isGetterMethod(obj.getDeclaredMethod("getName")));
			assertTrue(isGetterMethod(obj.getDeclaredMethod("isEnabled")));

			assertFalse(isGetterMethod(obj.getDeclaredMethod("setName", String.class)));
			assertFalse(isGetterMethod(obj.getDeclaredMethod("get")));
			assertFalse(isGetterMethod(obj.getDeclaredMethod("set", String.class)));
		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test(expected = NullPointerException.class)
	public void testIsGetterMethodException()
	{
		isGetterMethod(null);
	}

	@Test
	public void testIsSetterMethodName()
	{
		try
		{
			assertTrue(isSetterMethodName(obj.getDeclaredMethod("setName", String.class).getName()));

			assertFalse(isSetterMethodName(obj.getDeclaredMethod("getName").getName()));
			assertFalse(isSetterMethodName(obj.getDeclaredMethod("isEnabled").getName()));
			assertFalse(isSetterMethodName(obj.getConstructor().getName()));
			assertFalse(isSetterMethodName(null));
			assertFalse(isSetterMethodName(""));
			assertFalse(isSetterMethodName(obj.getDeclaredMethod("get").getName()));
			assertFalse(isSetterMethodName(obj.getDeclaredMethod("set", String.class).getName()));
			assertFalse(isSetterMethodName("value"));
		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test
	public void testIsSetterMethod()
	{
		try
		{
			assertTrue(isSetterMethod(obj.getDeclaredMethod("setName", String.class)));

			assertFalse(isSetterMethod(obj.getDeclaredMethod("getName")));
			assertFalse(isSetterMethod(obj.getDeclaredMethod("isEnabled")));
			assertFalse(isSetterMethod(obj.getDeclaredMethod("get")));
			assertFalse(isSetterMethod(obj.getDeclaredMethod("set", String.class)));
		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test(expected = NullPointerException.class)
	public void testIsSetterMethodException()
	{
		isSetterMethod(null);
	}

	@Test
	public void testGetSetterMethodField()
	{
		try
		{
			Field fieldName = obj.getField("name");
			Field fieldEnabled = obj.getField("enabled");
			Field fieldPrice = obj.getField("price");

			Method methodSetName = obj.getMethod("setName", fieldName.getType());
			Method methodSetEnabled = obj.getMethod("setEnabled", fieldEnabled.getType());

			assertEquals(methodSetName, getSetterMethod(fieldName, obj));
			assertEquals(methodSetEnabled, getSetterMethod(fieldEnabled, obj));

			assertNull(getSetterMethod(fieldPrice, obj));
		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test(expected = NullPointerException.class)
	public void testGetSetterMethodFieldException1()
	{
		getSetterMethod((Field)null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testGetSetterMethodFieldException2() throws SecurityException, NoSuchFieldException
	{
		getSetterMethod(obj.getField("price"), null);
	}

	@Test(expected = NullPointerException.class)
	public void testGetSetterMethodFieldException3()
	{
		getSetterMethod((Field)null, obj);
	}

	@Test
	public void testGetSetterMethodMethod()
	{
		try
		{
			Method methodSetName = obj.getMethod("setName", String.class);
			Method methodGetName = obj.getMethod("getName");
			Method methodSetEnabled = obj.getMethod("setEnabled", boolean.class);
			Method methodIsEnabled = obj.getMethod("isEnabled");
			Method methodSetInvalid = obj.getMethod("set", String.class);
			Method methodGetInvalid = obj.getMethod("get");

			assertEquals(methodSetName, getSetterMethod(methodSetName, obj));
			assertEquals(methodSetName, getSetterMethod(methodGetName, obj));
			assertEquals(methodSetEnabled, getSetterMethod(methodSetEnabled, obj));
			assertEquals(methodSetEnabled, getSetterMethod(methodIsEnabled, obj));

			assertNull(getSetterMethod(methodSetInvalid, obj));
			assertNull(getSetterMethod(methodGetInvalid, obj));
		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test(expected = NullPointerException.class)
	public void testGetSetterMethodMethodException1()
	{
		getSetterMethod((Method)null, null);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testGetSetterMethodMethodException2() throws SecurityException, NoSuchMethodException
	{
		getSetterMethod(obj.getMethod("getName", String.class), null);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testGetSetterMethodMethodException3() throws SecurityException, NoSuchMethodException
	{
		getSetterMethod(obj.getMethod("setName"), null);
	}

	@Test(expected = NullPointerException.class)
	public void testGetSetterMethodMethodException4()
	{
		getSetterMethod((Method)null, obj);
	}

	@Test
	public void testGetSetterMethod()
	{
		try
		{
			AccessibleObject methodSetName = obj.getMethod("setName", String.class);
			AccessibleObject methodGetName = obj.getMethod("getName");
			AccessibleObject methodSetEnabled = obj.getMethod("setEnabled", boolean.class);
			AccessibleObject methodIsEnabled = obj.getMethod("isEnabled");
			AccessibleObject methodSetInvalid = obj.getMethod("set", String.class);
			AccessibleObject methodGetInvalid = obj.getMethod("get");

			AccessibleObject fieldName = obj.getField("name");
			AccessibleObject fieldEnabled = obj.getField("enabled");
			AccessibleObject fieldPrice = obj.getField("price");

			assertEquals(methodSetName, getSetterMethod(methodSetName, obj));
			assertEquals(methodSetName, getSetterMethod(methodGetName, obj));
			assertEquals(methodSetEnabled, getSetterMethod(methodSetEnabled, obj));
			assertEquals(methodSetEnabled, getSetterMethod(methodIsEnabled, obj));
			assertEquals(methodSetName, getSetterMethod(fieldName, obj));
			assertEquals(methodSetEnabled, getSetterMethod(fieldEnabled, obj));

			assertNull(getSetterMethod(methodSetInvalid, obj));
			assertNull(getSetterMethod(methodGetInvalid, obj));
			assertNull(getSetterMethod(fieldPrice, obj));
		}
		catch(IllegalArgumentException e)
		{
			fail("IllegalArgumentException must not be thrown in this test case");
		}
		catch(Exception e)
		{
			fail(e.getMessage());
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodIllegalArgumentException1()
	{
		getSetterMethod((AccessibleObject)null, null);
	}

	@Test(expected = NullPointerException.class)
	public void testGetSetterMethodIllegalArgumentException2() throws IllegalArgumentException, NullPointerException,
	                SecurityException, NoSuchFieldException
	{
		getSetterMethod((AccessibleObject)obj.getField("price"), null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodIllegalArgumentException3()
	{
		getSetterMethod((AccessibleObject)null, obj);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testGetSetterMethodIllegalArgumentException4() throws IllegalArgumentException, NullPointerException,
	                SecurityException, NoSuchMethodException
	{
		getSetterMethod((AccessibleObject)obj.getMethod("getName", String.class), null);
	}

	@Test(expected = NoSuchMethodException.class)
	public void testGetSetterMethodIllegalArgumentException5() throws IllegalArgumentException, NullPointerException,
	                SecurityException, NoSuchMethodException
	{
		getSetterMethod((AccessibleObject)obj.getMethod("setName"), null);
	}

	@Test
	public void testGetPropertyNameByMethodName()
	{
		assertEquals("name", getPropertyNameByMethodName("getName"));
		assertEquals("name", getPropertyNameByMethodName("setName"));
		assertEquals("enabled", getPropertyNameByMethodName("setEnabled"));
		assertEquals("enabled", getPropertyNameByMethodName("isEnabled"));
		assertEquals("passWord", getPropertyNameByMethodName("getPassWord"));
		assertEquals("passWord", getPropertyNameByMethodName("isPassWord"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameByMethodNameException1()
	{
		getPropertyNameByMethodName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameByMethodNameException2()
	{
		getPropertyNameByMethodName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameByMethodNameException3()
	{
		getPropertyNameByMethodName("get");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameByMethodNameException4()
	{
		getPropertyNameByMethodName("is");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameByMethodNameException5()
	{
		getPropertyNameByMethodName("set");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameByMethodNameException6()
	{
		getPropertyNameByMethodName("onPassWord");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetPropertyNameByMethodNameException7()
	{
		getPropertyNameByMethodName("temp");
	}

	@Test
	public void testGetSetterMethodNameByGetterMethodName()
	{
		assertEquals("setName", getSetterMethodNameByGetterMethodName("getName"));
		assertEquals("setEnabled", getSetterMethodNameByGetterMethodName("isEnabled"));
		assertEquals("setPassWord", getSetterMethodNameByGetterMethodName("getPassWord"));
		assertEquals("setValId", getSetterMethodNameByGetterMethodName("isValId"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameByGetterMethodNameException1()
	{
		getSetterMethodNameByGetterMethodName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameByGetterMethodNameException2()
	{
		getSetterMethodNameByGetterMethodName("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameByGetterMethodNameException3()
	{
		getSetterMethodNameByGetterMethodName("get");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameByGetterMethodNameException4()
	{
		getSetterMethodNameByGetterMethodName("is");
	}

	@Test
	public void testGetSetterMethodNameByPropertyName()
	{
		assertEquals("setName", getSetterMethodNameByPropertyName("name"));
		assertEquals("setEnabled", getSetterMethodNameByPropertyName("enabled"));
		assertEquals("setPassWord", getSetterMethodNameByPropertyName("PassWord"));
		assertEquals("setN", getSetterMethodNameByPropertyName("n"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameByPropertyNameException1()
	{
		getSetterMethodNameByPropertyName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetSetterMethodNameByPropertyNameException2()
	{
		getSetterMethodNameByPropertyName("");
	}
}

class Foo
{
	public String name;
	public boolean enabled;
	public float price;

	public Foo()
	{}

	public String getName()
	{
		return name;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void get()
	{}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public void set(String value)
	{}
}
