package cz.fallingfox.matice;

import java.util.Arrays;
import java.util.Random;

class Matice implements IMatice {
    // *** Konstanty
    private static final Random RNG = new Random(); // generátor náhodných čísel

    // *** Atributy
    private int rows, columns; // počet řádků a sloupců matice
    private int[] values; // samotné hodnoty matice v jednorozměrném poli

    // *** Konstruktor
    public Matice(int rows, int columns) throws MaticeException {
        // kontrola vstupních parametrů
        if (rows <= 0 || columns <= 0)
            throw new MaticeException("Neplatné rozměry matice!");

        this.rows = rows;
        this.columns = columns;
        this.values = new int[rows * columns];
    }

    // *** Kopírovací konstruktor
    public Matice(Matice other) {
        this.rows = other.rows;
        this.columns = other.columns;
        this.values = Arrays.copyOf(other.values, other.values.length);
    }

    // *** Statické metody
    // Metoda pro vytvoření matice s náhodnými hodnotami
    public static Matice rng(int rows, int columns, int min, int max) throws MaticeException {
        Matice m = new Matice(rows, columns);

        for (int i = 0; i < m.values.length; i++) {
            m.values[i] = min + RNG.nextInt(max - min + 1);
        }

        return m;
    }

    // *** Metody
    @Override
    public int get(int row, int column) throws MaticeException {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns)
            throw new MaticeException("Neplatný řádek/sloupec!");
        return this.values[row * this.columns + column];
    }

    private int getUnsafe(int row, int column) {
        return this.values[row * this.columns + column];
    }

    @Override
    public void set(int row, int column, int value) throws MaticeException {
        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns)
            throw new MaticeException("Neplatný řádek/sloupec!");
        this.values[row * this.columns + column] = value;
    }

    private void setUnsafe(int row, int column, int value) {
        this.values[row * this.columns + column] = value;
    }

    @Override // Metoda pro prohození hodnot pod hlavní diagonálou
    public IMatice[] switchValues(IMatice other) throws MaticeException {
        Matice o = (Matice) other;
        Matice a = new Matice(this);
        Matice b = new Matice(o);
        if (!this.isSameDimension(o))
            throw new MaticeException("Matice nejsou stejných rozměrů!");
        
        for (int row = 0; row < Math.min(this.rows, this.columns); row++) {
            for (int column = 0; column <= row - 1; column++) {
                // System.out.printf("%d %d", row, column);
                int temp = a.getUnsafe(row, column);
                a.setUnsafe(row, column, b.getUnsafe(row, column));
                b.setUnsafe(row, column, temp);
            }
        }

        return new IMatice[] {a, b};
    }

    private boolean isSameDimension(Matice other) {
        return (this.rows == other.rows) && (this.columns == other.columns);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                sb.append(String.format("%3d ", getUnsafe(row, column)));
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('\n');
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }
}