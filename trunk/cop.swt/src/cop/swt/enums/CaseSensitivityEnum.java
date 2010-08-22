package cop.swt.enums;

import static cop.common.extensions.StringExtension.isNotEmpty;

import org.eclipse.core.runtime.Assert;

public enum CaseSensitivityEnum
{
	CASE_SENSITIVE
	{
		@Override
		public boolean matches(String str, String regex)
		{
			Assert.isNotNull(str);
			Assert.isLegal(isNotEmpty(regex));

			return isNotEmpty(str) ? str.matches(regex) : false;
		}
	},
	CASE_INSENSITIVE
	{
		@Override
		public boolean matches(String str, String regex)
		{
			Assert.isNotNull(str);
			Assert.isLegal(isNotEmpty(regex));

			return isNotEmpty(str) ? str.toLowerCase().matches(regex.toLowerCase()) : false;
		}
	};

	public abstract boolean matches(String str, String regex);

	public static CaseSensitivityEnum parseCaseSensitivity(boolean enabled)
	{
		return enabled ? CASE_SENSITIVE : CASE_INSENSITIVE;
	}
}
