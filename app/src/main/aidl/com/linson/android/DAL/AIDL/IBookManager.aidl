// IBookManager.aidl
package com.linson.android.DAL.AIDL;

import com.linson.android.DAL.AIDL.Book;

// Declare any non-default types here with import statements

interface IBookManager {
    void addBook(in Book book);
    List<Book> getList();
}