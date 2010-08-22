package cop;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import cop.algorithms.AlgorithmsTestSuit;
import cop.common.CommonTestSuit;

@RunWith(Suite.class)
@Suite.SuiteClasses({ AlgorithmsTestSuit.class, CommonTestSuit.class })
public class TestSuit
{}
