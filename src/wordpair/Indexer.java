/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordpair;

/**
 *
 * @author caleb
 */
public class Indexer {
    char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    int[] indexHolder = new int[27];

    public int arrayPosition(char character) {
        character = Character.toLowerCase(character);
        int arrayPos = 0;

        switch (character) {
            case 'a':
                arrayPos = 1;
                break;
            case 'b':
                arrayPos = 2;
                break;
            case 'c':
                arrayPos = 3;
                break;
            case 'd':
                arrayPos = 4;
                break;
            case 'e':
                arrayPos = 5;
                break;
            case 'f':
                arrayPos = 6;
                break;
            case 'g':
                arrayPos = 7;
                break;
            case 'h':
                arrayPos = 8;
                break;
            case 'i':
                arrayPos = 9;
                break;
            case 'j':
                arrayPos = 10;
                break;
            case 'k':
                arrayPos = 11;
                break;
            case 'l':
                arrayPos = 12;
                break;
            case 'm':
                arrayPos = 13;
                break;
            case 'n':
                arrayPos = 14;
                break;
            case 'o':
                arrayPos = 15;
                break;
            case 'p':
                arrayPos = 16;
                break;
            case 'q':
                arrayPos = 17;
                break;
            case 'r':
                arrayPos = 18;
                break;
            case 's':
                arrayPos = 19;
                break;
            case 't':
                arrayPos = 20;
                break;
            case 'u':
                arrayPos = 21;
                break;
            case 'v':
                arrayPos = 22;
                break;
            case 'w':
                arrayPos = 23;
                break;
            case 'x':
                arrayPos = 24;
                break;
            case 'y':
                arrayPos = 25;
                break;
            case 'z':
                arrayPos = 26;
                break;

            default:
                break;
        }

        return arrayPos;
    }

    public void updateIndex(char character, int index) {
        this.indexHolder[this.arrayPosition(character)] = index;
    }

    public int getIndex(char character) {
        return this.indexHolder[this.arrayPosition(character)];
    }
}
