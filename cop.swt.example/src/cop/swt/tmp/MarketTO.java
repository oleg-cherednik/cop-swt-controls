package cop.swt.tmp;

import static cop.common.extensions.CommonExtension.isNull;
import static cop.common.extensions.StringExtension.isEmpty;
import static cop.swt.widgets.enums.ImageTextViewEnum.IMAGE_ONLY;

import org.eclipse.swt.widgets.Widget;

import cop.swt.widgets.annotations.Column;
import cop.swt.widgets.annotations.ImageTextView;
import cop.swt.widgets.viewers.interfaces.PModifyProvider;

public class MarketTO
{
	private static final String USER_NAME = "Пользователь";
	private static final String OPERATOR_NAME = "Оператор";
	private static final String MANAGER_NAME = "Менеджер";

	@Column(name = "Маркет", order = 0, sortable = false, width = 30)
	private String marketName;
	@Column(name = USER_NAME, order = 1)
	@ImageTextView(view = IMAGE_ONLY)
	private boolean user;
	@Column(name = OPERATOR_NAME, order = 2)
	@ImageTextView(view = IMAGE_ONLY)
	private boolean operator;
	@Column(name = MANAGER_NAME, order = 3)
	@ImageTextView(view = IMAGE_ONLY)
	private boolean manager;

	public MarketTO()
	{}

	public MarketTO(String marketName)
	{
		this.marketName = marketName;
	}

	// /@Column(name = "Маркет", order = 0)
	public String getMarketName()
	{
		return marketName;
	}

	public boolean isUser()
	{
		return user;
	}

	public boolean isOperator()
	{
		return operator;
	}

	public boolean isManager()
	{
		return manager;
	}

	public void setMarketName(String marketName)
	{
		this.marketName = marketName;
	}

	public void setUser(boolean user)
	{
		this.user = user;
	}

	public void setOperator(boolean operator)
	{
		this.operator = operator;
	}

	public void setManager(boolean manager)
	{
		this.manager = manager;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("MarketTO [manager=");
		builder.append(manager);
		builder.append(", operator=");
		builder.append(operator);
		builder.append(", user=");
		builder.append(user);
		builder.append(", userName=");
		builder.append(marketName);
		builder.append("]");
		return builder.toString();
	}

	public static PModifyProvider<MarketTO> checkStrategy = new PModifyProvider<MarketTO>()
	{
		@Override
		public boolean canModify(Widget widget, MarketTO item, String key)
		{
			if(isNull(item) || isEmpty(key))
				return false;

			if(key.equals(USER_NAME))
				return item.user || item.operator || item.manager;
			if(key.equals(OPERATOR_NAME))
				return !item.user || item.operator;
			if(key.equals(MANAGER_NAME))
				return !item.user || !item.operator || item.manager;

			return false;
		}
	};
}
