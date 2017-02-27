package edu.neu.madcourse.numad17s_emmaliu.wordGame;

/**
 * Created by emma on 2/4/17.
 */

import java.util.HashMap;
import java.util.Map;

class Node {
    char c;
    HashMap<Character, Node> children = new HashMap<>();
    boolean isWord;

    public Node() {

    }

    public Node(char c) {
        this.c = c;
    }

}


public class Trie {
    private Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        HashMap<Character, Node> children = root.children;
        /** only insert word whose length <= 9 **/
        if (word.length() <= 9) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                Node node;
                if (children.containsKey(c)) {
                    node = children.get(c);
                } else {
                    node = new Node(c);
                    children.put(c, node);
                }
                children = node.children;

                if (i == word.length() - 1) {
                    node.isWord = true;
                }

            }
        }

    }

    public boolean search(String word) {
        Node node = searchNode(word);

        if (node != null && node.isWord) {
            return true;
        } else {
            return false;
        }
    }

    public boolean startWith(String prefix) {
        if (searchNode(prefix) == null) {
            return false;
        } else {
            return true;
        }
    }

    public Node searchNode(String word) {
        Map<Character, Node> children = root.children;
        Node node = null;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (children.containsKey(c)) {
                node = children.get(c);
                children = node.children;
            } else {
                return null;
            }
        }
        return node;
    }


}

