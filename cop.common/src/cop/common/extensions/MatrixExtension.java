/**
 * <b>License</b>: <a href="http://www.gnu.org/licenses/lgpl.html">GNU Leser General Public License</a>
 * <b>Copyright</b>: <a href="mailto:abba-best@mail.ru">Cherednik, Oleg</a>
 * 
 * $Id$
 * $HeadURL$
 */
package cop.common.extensions;

/**
 * @author <a href="mailto:abba-bestl@mail.ru">Cherednik, Oleg</a>
 */
public final class MatrixExtension
{
	private MatrixExtension()
	{}

	/**
	 * Create transpose matrix of giving one
	 * 
	 * @param matrix matrix
	 * @return new matrix that is transpose to giving <b>matrix</b>
	 */
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
