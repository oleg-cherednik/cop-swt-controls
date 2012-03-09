package cop.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cop.test.beans.JavaBeanTest;
import cop.test.extensions.ExtTestSuit;

@RunWith(Suite.class)
@Suite.SuiteClasses({ JavaBeanTest.class, ExtTestSuit.class })
public class CommonTestSuit
{}
