package miscellaneous;

import java.util.HashMap;
import java.util.Map;

public class Fibonacci {


    public static void main(String[] args) {

        long n = 24;

        System.out.println("fibonacci with memoization: " +  fibWithMemoization(n));
        System.out.println("fibonacci bottom up approach: " +  fibBottomUp(n));
        System.out.println("fibonacci bottom up space optimized approach: " +  fibBottomUpSpaceOptimized(n));
        System.out.println("fibonacci matrix multiplication approach: " +  fibMatrixMultiply(n));
        System.out.println("fibonacci matrix multiplication recursive approach: " +  fibMatrixMultiplyRecursive(n));
        System.out.println("fibonacci matrix multiplication recursive approach with memoization! : " +  fibMatrixMultiplyRecursiveMemoized(n));
        System.out.println("fibonacci simple: " +  simpleFib(n));
    }

    /**
     * simple fibonacci
     */

    public static long simpleFib(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1L;
        return  simpleFib(n-1) + simpleFib(n-2);
    }

    /**
     * fibonacci with memoization
     */

    public static long fibWithMemoization(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1L;
        Map<Long, Long> computedFibs = new HashMap<>();
        return fibWithMemoizationHelper(n-1, computedFibs) + fibWithMemoizationHelper(n-2, computedFibs);
    }

    public static long fibWithMemoizationHelper(long n, Map<Long, Long> computedFibs) {
        long fibResult;
        if(n == 0) {
            fibResult = 0;
            computedFibs.put(n, fibResult);
            return fibResult;
        }
        if(n == 1) {
            fibResult = 1;
            computedFibs.put(n, fibResult);
            return fibResult;
        }

        if(computedFibs.containsKey(n)) {
            return computedFibs.get(n);
        }

        fibResult = fibWithMemoizationHelper(n-1, computedFibs) + fibWithMemoizationHelper(n-2, computedFibs);
        computedFibs.put(n,fibResult);

        return fibResult;
    }

    /**
     * fibonacci with bottom up approach and O(n) space complexity
     */

    public static long fibBottomUp(long n) {
        Map<Long, Long> fibStore = new HashMap<>();

        for(long i=0; i<=n; i++) {
            if (i == 0) {
                fibStore.put(i,0L);
            } else if (i == 1) {
                fibStore.put(i,1L);

            } else {
                long computedFib = fibStore.get(i-1) + fibStore.get(i-2);
                fibStore.put(i, computedFib);
            }
        }

        return fibStore.get(n);

    }

    /**
     * fibonacci with bottom up approach and O(1) space complexity
     */

    public static long fibBottomUpSpaceOptimized(long n) {
        long first = 0, second = 0, result = 0;

        for(long i =0; i<=n; i++) {
            if(i==0) {
                second = 0;
            } else if(i==1) {
                first = 1;
            } else {
                result = first + second;
                second = first;
                first = result;
            }
        }
        return result;
    }


    /**
     * Matrix multiplication approaches down below.
     */

    public static long fibMatrixMultiply(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        long[][] allegedMatrix = {{1,1}, {1,0}};

        long[][] result = new long[2][2];

        for (long i = 1; i<=n-1 ; i++ ) {
            if (i == 1) {
                result = allegedMatrix;
            } else {
                result = mutiply2dMatrices(result, allegedMatrix);
            }
        }

        return result[0][0];

    }

    /**
     * Matrix multiplication done recursively to find miscellaneous.Fibonacci number
     */

    public static long fibMatrixMultiplyRecursive(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        long[][] allegedMatrix = {{1,1}, {1,0}};

        return twodMatrixPowerRecursive(n-1, allegedMatrix)[0][0];

    }

    public static long[][] twodMatrixPowerRecursive(long n, long[][] matrix) {

        if (n == 1) return matrix;
        /**
         * actually i used another approach where
         * return mutiply2dMatrices(twodMatrixPowerRecursive(n-1, matrix), matrix);
         * but that actually divides in n subproblems, this is much easier
         */

        if (n%2 == 0) {
            long[][] result = twodMatrixPowerRecursive(n/2, matrix);
            return mutiply2dMatrices(result, result);
        } else {
            long[][] result = twodMatrixPowerRecursive(n-1, matrix);
            return mutiply2dMatrices(result, matrix );
        }

    }

    /**
     * Matrix multiplication done recursively and memoized to find miscellaneous.Fibonacci number
     */

    public static long fibMatrixMultiplyRecursiveMemoized(long n) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        long[][] allegedMatrix = {{1,1}, {1,0}};

        Map<Long, long[][]> memoizedMap = new HashMap<>();

        return twodMatrixPowerRecursiveMemoized(n-1, allegedMatrix, memoizedMap)[0][0];

    }

    public static long[][] twodMatrixPowerRecursiveMemoized(long n, long[][] matrix, Map<Long, long[][]> memoizationMap) {

        if (n == 1) {
            memoizationMap.put(n, matrix);
            return matrix;
        }

        if (memoizationMap.containsKey(n)) {
            return memoizationMap.get(n);
        }

        if (n%2 == 0) {
            long[][] subProblem = twodMatrixPowerRecursiveMemoized(n/2, matrix, memoizationMap);

            long[][] result = mutiply2dMatrices(subProblem, subProblem);
            memoizationMap.put(n, result);

            return result;
        } else {
            long[][] subProblem = twodMatrixPowerRecursiveMemoized(n-1, matrix, memoizationMap);

            long[][] result = mutiply2dMatrices(subProblem, matrix);
            memoizationMap.put(n, result);

            return result;
        }

    }

    /**
     * util function to multiply two matrices
     */

    public static long[][] mutiply2dMatrices(long[][] matrix1, long[][] matrix2) {

        long[][] resultMatrix = new long[2][2];

        resultMatrix[0][0] = matrix1[0][0] * matrix2[0][0] + matrix1[0][1] * matrix2[1][0];
        resultMatrix[0][1] = matrix1[0][0] * matrix2[0][1] + matrix1[0][1] * matrix2[1][1];
        resultMatrix[1][0] = matrix1[1][0] * matrix2[0][0] + matrix1[1][1] * matrix2[1][0];
        resultMatrix[1][1] = matrix1[1][0] * matrix2[0][1] + matrix2[1][1] * matrix2[1][1];

        return resultMatrix;
    }


}
