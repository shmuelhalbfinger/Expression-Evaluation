/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shmuel_halbfinger_23408222_project1;

import java.util.NoSuchElementException;

/**
 *
 * @author samda
 */
public class ListStack<E> implements Stack<E> {

    private int size = 0;

    private class Node {

        private E val;
        private Node next;

        public Node(E val) {
            this.val = val;
        }
    }
    private Node head;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void push(E val) {
        Node node = new Node(val);
        if (head != null) {
            node.next = head;
        }
        head = node;
        size++;
    }

    @Override
    public E top() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return head.val;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node node = head;
        head = head.next;
        size--;
        return node.val;
    }
}
