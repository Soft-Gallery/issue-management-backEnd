package com.softgallery.issuemanagementbackEnd.service.issue;

public enum MainCause {
    RESOLVING,     // 이슈 해결 중 (default value)
    TYPO,          // 단순 오타,
    FEATURE,       // 프로그램 기능 구현 문제 (function, method ...)
    LOGIC,         // 논리구조 문제 (예외처리 누락, 고려하지 못한 Use Case 등)
    STRUCTURE,     // 프로그램 설계 구조 문제
    CONFIGURATION, // 설정 및 외부 dependency 문제
    INFRA,         // DB, 네트워크, 클라우드 등 Infrastructure 문제
    DOCUMENTATION, // 문서화 문제
}
