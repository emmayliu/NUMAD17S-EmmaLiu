package edu.neu.madcourse.numad17s_emmaliu;

/**
 * Created by emma on 2/4/17.
 */



class Node {

    //assume we only have 26 character based on provided wordlist
    private Node[] children;
    private boolean isEnd;

    //constructor
    public Node() {
        this.children = new Node[26];
    }

    public void setEnd() {
        isEnd = true;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public boolean contains (char c) {
        if (children[c - 'a'] != null) {
            return true;
        } else {
            return false;
        }
    }

    public void set(char c, Node node) {
        children[c - 'a'] = node;
    }

    public Node get (char c) {
        return children[c - 'a'];
    }
}


public class Trie {
    private Node root;

    //constructor
    public Trie() {
        root = new Node();
    }

    //1. Insert a word into trie

    public void insert(String word) {
        Node curNode = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!curNode.contains(c)) {
                curNode.set(c, new Node());
            }
            curNode = curNode.get(c);
        }
        curNode.setEnd();
    }

    //2. Search Prefix
    private Node searchPrefix(String word) {
        Node curNode = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (curNode.contains(c)) {
                curNode = curNode.get(c);
            } else {
                return null;
            }
        }
        return curNode;
    }

    //3. Search word

    public boolean search(String word) {
        Node node = searchPrefix(word);
        if (node != null && node.isEnd()) {
            return true;
        } else {
            return false;
        }
    }

}

