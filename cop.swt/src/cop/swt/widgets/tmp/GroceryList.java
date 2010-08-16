package cop.swt.widgets.tmp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GroceryList
{
	// Holds the foods
	private List<Food> foods;

	/**
	 * Constructs a grocery list
	 */
	public GroceryList()
	{
		foods = new ArrayList<Food>();

		// Add some foods
		foods.add(new Food("Broccoli", true));
		foods.add(new Food("Bundt Cake", false));
		foods.add(new Food("Cabbage", true));
		foods.add(new Food("Candy Canes", false));
		foods.add(new Food("Eggs", true));
		foods.add(new Food("Potato Chips", false));
		foods.add(new Food("Milk", true));
		foods.add(new Food("Soda", false));
		foods.add(new Food("Chicken", true));
		foods.add(new Food("Cinnamon Rolls", false));
	}

	/**
	 * Returns the foods in this grocery list
	 * 
	 * @return List
	 */
	public List<Food> getFoods()
	{
		return Collections.unmodifiableList(foods);
	}
}
