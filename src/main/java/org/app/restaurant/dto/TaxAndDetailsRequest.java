package org.app.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.app.restaurant.entity.Tax;
import org.app.restaurant.entity.TaxDetails;

@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaxAndDetailsRequest {

    private Tax tax;

    private TaxDetails taxDetails;
}


