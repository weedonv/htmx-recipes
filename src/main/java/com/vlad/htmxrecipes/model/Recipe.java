package com.vlad.htmxrecipes.model;

import org.springframework.data.annotation.Id;

public record Recipe(@Id Integer id, String name, String description) {
}
