package com.gisma.competition.acm.persistence.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisma.competition.acm.api.exception.ValidationException;
import com.gisma.competition.acm.persistence.enumeration.ArgumentTypeModel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class TestCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer testCaseId;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "competition_id", nullable = false)
    private Competition competition;

    @Column(nullable = false)
    private String value;

    @Column(nullable = false)
    private ArgumentTypeModel argumentType;

    public JsonNode getJsonValue() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(value);
        } catch (JsonProcessingException ex) {
            ValidationException validationException = new ValidationException();
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("JsonProcessing", ex.getMessage());
            validationException.setDetails(errorDetails);
            throw validationException;
        }
    }
}
