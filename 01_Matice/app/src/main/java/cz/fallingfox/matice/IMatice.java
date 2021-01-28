package cz.fallingfox.matice;

interface IMatice {
    int get(int row, int column) throws MaticeException;

    void set(int row, int column, int value) throws MaticeException;

    IMatice[] switchValues(IMatice other) throws MaticeException;
}