package cop.common.extensions;

public final class MatrixExtension
{
	private MatrixExtension()
	{}

	public static int[][] transposeMatrix(int[][] matrix)
	{
		if(matrix == null)
			return null;

		int columns = 0;
		int rows = matrix.length;

		for(int[] row : matrix)
			columns = Math.max(columns, row.length);

		int[][] arr = new int[columns][rows];

		for(int i = 0; i < columns; i++)
			for(int j = 0; j < rows; j++)
				arr[i][j] = matrix[j][i];

		return arr;
	}
}
