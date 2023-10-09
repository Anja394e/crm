package crm.call;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers/{id}")
public class CallController {

    private final CallService callService;

    @Autowired
    public CallController(CallService callService) {
        this.callService = callService;
    }

    @GetMapping(path = "/calls")
    public List<Call> getCalls(@PathVariable ("id") int customerId) {
        return callService.getCalls(customerId);
    }

    @PostMapping(path = "/call/start")
    public void registerStart(@PathVariable ("id") int customerId) {
        callService.addStart(customerId);
    }

    @PostMapping(path = "/call/end")
    public void registerEnd(@PathVariable ("id") int customerId,
                            @Valid @RequestBody Call call) {
        callService.addEnd(customerId, call);
    }

    @DeleteMapping(path = "/calls/{callId}")
    public void deleteCall(@PathVariable ("id") int customerId,
            @PathVariable("callId") int callId) {
        callService.deleteCall(callId);
    }
}