package fooddelivery.external;

import java.util.Date;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "store", url = "${api.url.store}")
public interface CookingService {
    @RequestMapping(
        method = RequestMethod.PUT,
        path = "/cookings/{id}/accept-or-reject"
    )
    public void acceptOrReject(
        @PathVariable("id") Long id,
        @RequestBody AcceptOrRejectCommand acceptOrRejectCommand
    );
}
