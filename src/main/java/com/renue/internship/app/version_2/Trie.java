package com.renue.internship.app.version_2;


import java.util.*;

public class Trie {
    private final Node root;



    public Trie() {
        this.root = new Node('\0');
    }

    public void insert(String word, int line) {
        Node root = this.root;
        for (int i = 0; i < word.length(); i++) {
            Node node = root.children.get(word.charAt(i));
            if (node != null) {
                node.lineNumbers.add(line);
            } else {
                Node newNode = new Node(word.charAt(i), line);
                root.children.put(word.charAt(i), newNode);
                node = newNode;
            }
            root = node;
        }
    }


    public Set<Integer> hits(String prefix) {
        Node currentNode = this.root;
        for (int i = 0; i < prefix.length(); i++) {
            Node nextNode = currentNode.children.get(prefix.charAt(i));
            if (nextNode == null) {
                return Collections.emptySet();
            } else {
                currentNode = nextNode;
            }
        }
        return currentNode.lineNumbers;
    }

    public static class Node {
        private final char c;
        private final Map<Character, Node> children;
        private final Set<Integer> lineNumbers;

        public Node(char c) {
            this.c = c;
            children = new HashMap<>();
            lineNumbers = new HashSet<>();
        }

        public Node(char c, Integer lineNumber) {
            this(c);
            lineNumbers.add(lineNumber);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return c == node.c && Objects.equals(children, node.children) && Objects.equals(lineNumbers, node.lineNumbers);
        }

        @Override
        public int hashCode() {
            return Objects.hash(c, children, lineNumbers);
        }
    }
}
