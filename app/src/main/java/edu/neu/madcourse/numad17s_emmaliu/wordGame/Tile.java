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

import java.util.Arrays;

import edu.neu.madcourse.numad17s_emmaliu.R;

import static android.graphics.Color.BLUE;
import static android.graphics.Color.TRANSPARENT;


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
    private static final int LEVEL_39 = 39;
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
    private int isSelected;
    private int backGroundColor;




    public Tile(GameFragment game) {

        this.mGame = game;
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


    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int value) {
        this.isSelected = value;
    }

    public void setBackGroundColor(int value) {
        this.backGroundColor = value;
    }

    public int getBackGroundColor() {
        return this.backGroundColor;
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
        this.isSelected = 0;
        this.backGroundColor = 0;
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
            setOwner(Owner.J);
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
        this.isSelected = 1;
        if (letter == 'A') setOwner(Owner.SA);
        if (letter == 'B') setOwner(Owner.SB);
        if (letter == 'C') setOwner(Owner.SC);
        if (letter == 'D') setOwner(Owner.SD);
        if (letter == 'E') setOwner(Owner.SE);
        if (letter == 'F') setOwner(Owner.SF);
        if (letter == 'G') setOwner(Owner.SG);
        if (letter == 'H') setOwner(Owner.SH);
        if (letter == 'I') setOwner(Owner.SI);
        if (letter == 'J') setOwner(Owner.SJ);
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

    public void unSelectLetter() {
        if (letter == 'A') setOwner(Owner.A);
        if (letter == 'B') setOwner(Owner.B);
        if (letter == 'C') setOwner(Owner.C);
        if (letter == 'D') setOwner(Owner.D);
        if (letter == 'E') setOwner(Owner.E);
        if (letter == 'F') setOwner(Owner.F);
        if (letter == 'G') setOwner(Owner.G);
        if (letter == 'H') setOwner(Owner.H);
        if (letter == 'I') setOwner(Owner.I);
        if (letter == 'J') setOwner(Owner.J);
        if (letter == 'K') setOwner(Owner.K);
        if (letter == 'L') setOwner(Owner.L);
        if (letter == 'M') setOwner(Owner.M);
        if (letter == 'N') setOwner(Owner.N);
        if (letter == 'O') setOwner(Owner.O);
        if (letter == 'P') setOwner(Owner.P);
        if (letter == 'Q') setOwner(Owner.Q);
        if (letter == 'R') setOwner(Owner.R);
        if (letter == 'S') setOwner(Owner.S);
        if (letter == 'T') setOwner(Owner.T);
        if (letter == 'U') setOwner(Owner.U);
        if (letter == 'V') setOwner(Owner.V);
        if (letter == 'W') setOwner(Owner.W);
        if (letter == 'X') setOwner(Owner.X);
        if (letter == 'Y') setOwner(Owner.Y);
        if (letter == 'Z') setOwner(Owner.Z);
        this.isSelected = 0;
        getView().setBackgroundColor(View.GONE);
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

    public void changeBackground() {
        this.backGroundColor = 1;
        getView().setBackgroundColor(BLUE);
    }
    public void removeBackgroud() {
        getView().setBackgroundColor(TRANSPARENT);
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
                level = LEVEL_BLANK;
                //level = mGame.isAvailable(this) ? LEVEL_AVAILABLE : LEVEL_BLANK;
                break;
        }
        return level;
    }

    public void animate() {
        Animator anim = AnimatorInflater.loadAnimator(mGame.getActivity(),
                R.animator.tictactoe);
        if (getView() != null) {
            anim.setTarget(getView());
            anim.start();
        }
    }

    @Override
    public String toString() {
        String s = "";
        s = s + "mGame : " + mGame
                + " letter : " + letter
                + " mSubTiles " + Arrays.toString(mSubTiles);
        return s;
    }
}
