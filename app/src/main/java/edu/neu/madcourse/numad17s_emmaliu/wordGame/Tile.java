/***
 * Excerpted from "Hello, Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/eband4 for more book information.
***/
    package edu.neu.madcourse.numad17s_emmaliu.wordGame;

    import android.animation.Animator;
    import android.animation.AnimatorInflater;
    import android.graphics.drawable.Drawable;
    import android.util.Log;
    import android.view.View;
    import android.widget.ImageButton;

    import edu.neu.madcourse.numad17s_emmaliu.R;

    public class Tile {

    public enum Owner {
    NEITHER, BOTH,
    A, B, C, D, E, F, G, H, I, J, K,
    L, M, N, O, P, Q, R, S, T, U, V,
    W, X, Y, Z,
    SA, SB, SC, SD, SE, SF, SG, SH, SI, SJ, SK, SL, SM, SN, SO, SP,
    SQ, SR, SS, ST, SU, SV, SW, SX, SY, SZ
    }

    // These levels are defined in the drawable definitions

    private static final int LEVEL_1 = 1;
    private static final int LEVEL_2 = 2;
    private static final int LEVEL_3 = 3;
    private static final int LEVEL_4 = 4;
    private static final int LEVEL_5 = 5;
    private static final int LEVEL_6 = 6;
    private static final int LEVEL_7 = 7;
    private static final int LEVEL_8 = 8;
    private static final int LEVEL_9 = 9;
    private static final int LEVEL_10 = 10;
    private static final int LEVEL_11 = 11;
    private static final int LEVEL_12 = 12;
    private static final int LEVEL_13 = 13;
    private static final int LEVEL_14 = 14;
    private static final int LEVEL_15 = 15;
    private static final int LEVEL_16 = 16;
    private static final int LEVEL_17 = 17;
    private static final int LEVEL_18 = 18;
    private static final int LEVEL_19 = 19;
    private static final int LEVEL_20 = 20;
    private static final int LEVEL_21 = 21;
    private static final int LEVEL_22 = 22;
    private static final int LEVEL_23 = 23;
    private static final int LEVEL_24 = 24;
    private static final int LEVEL_25 = 25;
    private static final int LEVEL_26 = 26;
    private static final int LEVEL_27 = 27;
    private static final int LEVEL_28 = 28;
    private static final int LEVEL_29 = 29;
    private static final int LEVEL_30 = 30;
    private static final int LEVEL_31 = 31;
    private static final int LEVEL_32 = 32;
    private static final int LEVEL_33 = 33;
    private static final int LEVEL_34 = 34;
    private static final int LEVEL_35 = 35;
    private static final int LEVEL_36 = 36;
    private static final int LEVEL_37 = 37;
    private static final int LEVEL_38 = 38;
    private static final int LEVEL_39 = 38;
    private static final int LEVEL_40 = 40;
    private static final int LEVEL_41 = 41;
    private static final int LEVEL_42 = 42;
    private static final int LEVEL_43 = 43;
    private static final int LEVEL_44 = 44;
    private static final int LEVEL_45 = 45;
    private static final int LEVEL_46 = 46;
    private static final int LEVEL_47 = 47;
    private static final int LEVEL_48 = 48;
    private static final int LEVEL_49 = 49;
    private static final int LEVEL_50 = 50;
    private static final int LEVEL_51 = 51;
    private static final int LEVEL_52 = 52;
    private static final int LEVEL_53 = 53;

    private static final int LEVEL_XX = 54;
    private static final int LEVEL_OO = 55;
    private static final int LEVEL_BLANK = 56;
    private static final int LEVEL_AVAILABLE = 57;
    private static final int LEVEL_TIE = 58;


    private final GameFragment mGame;
    private Owner mOwner = Owner.NEITHER;
    private View mView;
    private Tile mSubTiles[];
    private char letter;



    public Tile(GameFragment game) {

        this.mGame = game;
    }

    public Tile deepCopy() {
        Tile tile = new Tile(mGame);
        tile.setOwner(getOwner());
        if (getSubTiles() != null) {
            Tile newTiles[] = new Tile[9];
            Tile oldTiles[] = getSubTiles();
            for (int child = 0; child < 9; child++) {
                newTiles[child] = oldTiles[child].deepCopy();
            }
            tile.setSubTiles(newTiles);
        }
        return tile;
    }

    public View getView() {
    return mView;
    }

    public void setView(View view) {
    this.mView = view;
    }

    public Owner getOwner() {
    return mOwner;
    }

    public void setOwner(Owner owner) {
    this.mOwner = owner;
    }

    public Tile[] getSubTiles() {
    return mSubTiles;
    }

    public void setSubTiles(Tile[] subTiles) {
        this.mSubTiles = subTiles;
    }

    public char getLetter() {
        return letter;
    }
    public void setLetter(char letter) {
        this.letter = letter;
        if (letter == 'A') {
            setOwner(Owner.A);
        }
        if (letter == 'B') {
            setOwner(Owner.B);
        }
        if (letter == 'C') {
            setOwner(Owner.C);
        }
        if (letter == 'D') {
            setOwner(Owner.D);
        }
        if (letter == 'E') {
            setOwner(Owner.E);
        }
        if (letter == 'F') {
            setOwner(Owner.F);
        }
        if (letter == 'G') {
            setOwner(Owner.G);
        }
        if (letter == 'H') {
            setOwner(Owner.H);
        }
        if (letter == 'I') {
            setOwner(Owner.I);
        }
        if (letter == 'J') {
            setOwner(Owner.G);
        }
        if (letter == 'K') {
            setOwner(Owner.K);
        }
        if (letter == 'L') {
            setOwner(Owner.L);
        }
        if (letter == 'M') {
            setOwner(Owner.M);
        }
        if (letter == 'N') {
            setOwner(Owner.N);
        }
        if (letter == 'O') {
            setOwner(Owner.O);
        }
        if (letter == 'P') {
            setOwner(Owner.P);
        }
        if (letter == 'Q') {
            setOwner(Owner.Q);
        }
        if (letter == 'R') {
            setOwner(Owner.R);
        }
        if (letter == 'S') {
            setOwner(Owner.S);
        }
        if (letter == 'T') {
            setOwner(Owner.T);
        }
        if (letter == 'U') {
            setOwner(Owner.U);
        }
        if (letter == 'V') {
            setOwner(Owner.V);
        }
        if (letter == 'W') {
            setOwner(Owner.W);
        }
        if (letter == 'X') {
            setOwner(Owner.X);
        }
        if (letter == 'Y') {
            setOwner(Owner.Y);
        }
        if (letter == 'Z') {
            setOwner(Owner.Z);
        }
    }

        public void selectLetter() {
            if (letter == 'A') setOwner(Owner.SA);
            if (letter == 'B') setOwner(Owner.SB);
            if (letter == 'C') setOwner(Owner.SC);
            if (letter == 'D') setOwner(Owner.SD);
            if (letter == 'E') setOwner(Owner.SE);
            if (letter == 'F') setOwner(Owner.SF);
            if (letter == 'G') setOwner(Owner.SG);
            if (letter == 'H') setOwner(Owner.SH);
            if (letter == 'I') setOwner(Owner.SI);
            if (letter == 'G') setOwner(Owner.SJ);
            if (letter == 'K') setOwner(Owner.SK);
            if (letter == 'L') setOwner(Owner.SL);
            if (letter == 'M') setOwner(Owner.SM);
            if (letter == 'N') setOwner(Owner.SN);
            if (letter == 'O') setOwner(Owner.SO);
            if (letter == 'P') setOwner(Owner.SP);
            if (letter == 'Q') setOwner(Owner.SQ);
            if (letter == 'R') setOwner(Owner.SR);
            if (letter == 'S') setOwner(Owner.SS);
            if (letter == 'T') setOwner(Owner.ST);
            if (letter == 'U') setOwner(Owner.SU);
            if (letter == 'V') setOwner(Owner.SV);
            if (letter == 'W') setOwner(Owner.SW);
            if (letter == 'X') setOwner(Owner.SX);
            if (letter == 'Y') setOwner(Owner.SY);
            if (letter == 'Z') setOwner(Owner.SZ);
            updateDrawableState();
        }


    public void updateDrawableState() {
        if (mView == null) return;
        int level = getLevel();
        if (mView.getBackground() != null) {
            mView.getBackground().setLevel(level);
        }
        if (mView instanceof ImageButton) {
            Drawable drawable = ((ImageButton) mView).getDrawable();
            drawable.setLevel(level);
        }
    }

    private int getLevel() {
        int level = LEVEL_BLANK;
        switch (mOwner) {
        case A:
        level = LEVEL_1;
        break;
        case B:
        level = LEVEL_2;
        break;
        case C:
        level = LEVEL_3;
        break;
        case D:
        level = LEVEL_4;
        break;
        case E:
        level = LEVEL_5;
        break;
        case F:
        level = LEVEL_6;
        break;
        case G:
        level = LEVEL_7;
        break;
        case H:
        level = LEVEL_8;
        break;
        case I:
        level = LEVEL_9;
        break;
        case J:
        level = LEVEL_10;
        break;
        case K:
        level = LEVEL_11;
        break;
        case L:
        level = LEVEL_12;
        break;
        case M:
        level = LEVEL_13;
        break;
        case N:
        level = LEVEL_14;
        break;
        case O:
        level = LEVEL_15;
        break;
        case P:
        level = LEVEL_16;
        break;
        case Q:
        level = LEVEL_17;
        break;
        case R:
        level = LEVEL_18;
        break;
        case S:
        level = LEVEL_19;
        break;
        case T:
        level = LEVEL_20;
        break;
        case U:
        level = LEVEL_21;
        break;
        case V:
        level = LEVEL_22;
        break;
        case W:
        level = LEVEL_23;
        break;
        case X:
        level = LEVEL_24;
        break;
        case Y:
        level = LEVEL_25;
        break;
        case Z:
        level = LEVEL_26;
        break;
        case SA:
        level = LEVEL_27;
        break;
        case SB:
        level = LEVEL_28;
        break;
        case SC:
        level = LEVEL_29;
        break;
        case SD:
        level = LEVEL_30;
        break;
        case SE:
        level = LEVEL_31;
        break;
        case SF:
        level = LEVEL_32;
        break;
        case SG:
        level = LEVEL_33;
        break;
        case SH:
        level = LEVEL_34;
        break;
        case SI:
        level = LEVEL_35;
        break;
        case SJ:
        level = LEVEL_36;
        break;
        case SK:
        level = LEVEL_37;
        break;
        case SL:
        level = LEVEL_38;
        break;
        case SM:
        level = LEVEL_39;
        break;
        case SN:
        level = LEVEL_40;
        break;
        case SO:
        level = LEVEL_41;
        break;
        case SP:
        level = LEVEL_42;
        break;
        case SQ:
        level = LEVEL_43;
        break;
        case SR:
        level = LEVEL_44;
        break;
        case SS:
        level = LEVEL_45;
        break;
        case ST:
        level = LEVEL_46;
        break;
        case SU:
        level = LEVEL_47;
        break;
        case SV:
        level = LEVEL_48;
        break;
        case SW:
        level = LEVEL_49;
        break;
        case SX:
        level = LEVEL_50;
        break;
        case SY:
        level = LEVEL_51;
        break;
        case SZ:
        level = LEVEL_52;
        break;
        case BOTH:
        level = LEVEL_TIE;
        break;
        case NEITHER:
        level = mGame.isAvailable(this) ? LEVEL_AVAILABLE : LEVEL_BLANK;
        break;
        }
        return level;
    }

    private void countCaptures(int totalX[], int totalO[]) {
    int capturedX, capturedO;
    // Check the horizontal
    for (int row = 0; row < 3; row++) {
    capturedX = capturedO = 0;
    for (int col = 0; col < 3; col++) {
    Owner owner = mSubTiles[3 * row + col].getOwner();
    if (owner == Owner.X || owner == Owner.BOTH) capturedX++;
    if (owner == Owner.O || owner == Owner.BOTH) capturedO++;
    }
    totalX[capturedX]++;
    totalO[capturedO]++;
    }

    // Check the vertical
    for (int col = 0; col < 3; col++) {
    capturedX = capturedO = 0;
    for (int row = 0; row < 3; row++) {
    Owner owner = mSubTiles[3 * row + col].getOwner();
    if (owner == Owner.X || owner == Owner.BOTH) capturedX++;
    if (owner == Owner.O || owner == Owner.BOTH) capturedO++;
    }
    totalX[capturedX]++;
    totalO[capturedO]++;
    }

    // Check the diagonals
    capturedX = capturedO = 0;
    for (int diag = 0; diag < 3; diag++) {
    Owner owner = mSubTiles[3 * diag + diag].getOwner();
    if (owner == Owner.X || owner == Owner.BOTH) capturedX++;
    if (owner == Owner.O || owner == Owner.BOTH) capturedO++;
    }
    totalX[capturedX]++;
    totalO[capturedO]++;
    capturedX = capturedO = 0;
    for (int diag = 0; diag < 3; diag++) {
    Owner owner = mSubTiles[3 * diag + (2 - diag)].getOwner();
    if (owner == Owner.X || owner == Owner.BOTH) capturedX++;
    if (owner == Owner.O || owner == Owner.BOTH) capturedO++;
    }
    totalX[capturedX]++;
    totalO[capturedO]++;
    }

    public Owner findWinner() {
    // If owner already calculated, return it
    if (getOwner() != Owner.NEITHER)
    return getOwner();

    int totalX[] = new int[4];
    int totalO[] = new int[4];
    countCaptures(totalX, totalO);
    if (totalX[3] > 0) return Owner.X;
    if (totalO[3] > 0) return Owner.O;

    // Check for a draw
    int total = 0;
    for (int row = 0; row < 3; row++) {
    for (int col = 0; col < 3; col++) {
    Owner owner = mSubTiles[3 * row + col].getOwner();
    if (owner != Owner.NEITHER) total++;
    }
    if (total == 9) return Owner.BOTH;
    }

    // Neither player has won this tile
    return Owner.NEITHER;
    }

    public int evaluate() {
    switch (getOwner()) {
    case X:
    return 100;
    case O:
    return -100;
    case NEITHER:
    int total = 0;
    if (getSubTiles() != null) {
    for (int tile = 0; tile < 9; tile++) {
    total += getSubTiles()[tile].evaluate();
    }
    int totalX[] = new int[4];
    int totalO[] = new int[4];
    countCaptures(totalX, totalO);
    total = total * 100 + totalX[1] + 2 * totalX[2] + 8 *
    totalX[3] - totalO[1] - 2 * totalO[2] - 8 * totalO[3];
    }
    return total;
    }
    return 0;
    }

    public void animate() {
    Animator anim = AnimatorInflater.loadAnimator(mGame.getActivity(),
    R.animator.tictactoe);
    if (getView() != null) {
    anim.setTarget(getView());
    anim.start();
    }
    }
    }
