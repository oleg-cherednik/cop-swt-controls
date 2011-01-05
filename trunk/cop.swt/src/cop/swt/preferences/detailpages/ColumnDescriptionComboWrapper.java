package cop.swt.preferences.detailpages;


public final class ColumnDescriptionComboWrapper<T>
{
//	private ColumnDescription<T> description;
//	private T obj;
//
//	public ColumnDescriptionComboWrapper(T obj, ColumnDescription<T> description)
//	{
//		Assert.isNotNull(obj);
//		Assert.isNotNull(description);
//
//		this.obj = obj;
//		this.description = description;
//	}
//
//	@Label
//	public String getName(Locale locale)
//	{
//		try
//		{
//			return getTranslation(obj, description.getKey(), locale);
//		}
//		catch(AnnotationDeclarationException e)
//		{
//			e.printStackTrace();
//
//			return description.getName();
//		}
//	}
//
//	public static <T> List<ColumnDescriptionComboWrapper<T>> createDescriptionWrapper(T obj,
//	                List<ColumnDescription<T>> descriptions)
//	{
//		if(isNull(obj) && isEmpty(descriptions))
//			return null;
//
//		List<ColumnDescriptionComboWrapper<T>> res = new ArrayList<ColumnDescriptionComboWrapper<T>>(
//		                descriptions.size());
//
//		for(ColumnDescription<T> description : descriptions)
//			res.add(new ColumnDescriptionComboWrapper<T>(obj, description));
//
//		return res;
//	}
}
