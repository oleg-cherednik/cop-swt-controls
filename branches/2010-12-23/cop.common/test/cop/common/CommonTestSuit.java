package cop.common;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cop.common.beans.JavaBeanTest;
import cop.common.extensions.ExtensionsTestSuit;

@RunWith(Suite.class)
@Suite.SuiteClasses({ JavaBeanTest.class, ExtensionsTestSuit.class })
public class CommonTestSuit
{}
