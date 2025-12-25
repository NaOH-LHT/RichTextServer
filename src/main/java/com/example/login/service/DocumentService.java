package com.example.login.service;

import java.util.Map;

public interface DocumentService {

    Map<String, Object> deleteDocument(String name);

    Map<String, Object> renameDocument(String oldName, String newName);
}
