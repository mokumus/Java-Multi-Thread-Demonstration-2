package com.muhammedokumus;

import java.security.InvalidParameterException;

/**
 * Provides matrix display utilities for normal and augmented matrices
 */
class Matrix {
    /**
     * A primitive 2D array of floats
     */
    private float[][] m;

    /**
     * @param m A primitive 2D array of floats
     */
    Matrix(float[][] m) { this.m = m; }

    Matrix(int rs, int cs) {
        this.m = new float[rs][cs];
    }

    Matrix() { }

    Matrix reInit(float[][] m){
        this.m = m;
        return this;
    }

    /**
     * @return A primitive 2D array of floats
     */
    float[][] getM() {
        return m;
    }

    int getRowSize() {
        return m.length;
    }

    int getColSize() {
        return m[0].length;
    }

    /**
     * Prints the Matrix in a user friendly way.
     */
    void display() {
        for (float[] floats : m) {
            for (int j = 0; j < m[0].length; j++) {
                if (j == 0)
                    System.out.print(floats[j]);
                else
                    System.out.print(String.format(",%6s", floats[j]));
            }
            System.out.println();
        }
    }


    Matrix add(Matrix m2) throws InvalidParameterException {
        if (this.getColSize() == m2.getColSize() && this.getRowSize() == m2.getRowSize()){
            for(int i = 0; i < getRowSize(); i++){
                for(int j = 0; j < getColSize(); j++){
                    this.getM()[i][j] = this.getM()[i][j] + m2.getM()[i][j];
                }
            }
            return this;
        }
        else{
            throw new InvalidParameterException("Matrix sizes are incompatible");
        }
    }
}