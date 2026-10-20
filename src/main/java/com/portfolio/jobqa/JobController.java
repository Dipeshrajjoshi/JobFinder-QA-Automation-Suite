package com.portfolio.jobqa;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final List<Job> jobs = List.of(
        new Job(1, "QA Engineer", "Northstar AI", "Remote", "Full-time"),
        new Job(2, "Junior Java Developer", "Maple Systems", "Toronto", "Full-time"),
        new Job(3, "Software Test Intern", "BrightPath", "Hybrid", "Internship"),
        new Job(4, "Automation Engineer", "CloudDesk", "Remote", "Full-time")
    );

    @GetMapping
    public List<Job> getJobs(@RequestParam(required = false) String q,
                             @RequestParam(required = false) String location) {
        return jobs.stream()
            .filter(j -> q == null || (j.title() + " " + j.company()).toLowerCase().contains(q.toLowerCase()))
            .filter(j -> location == null || location.isBlank() || j.location().equalsIgnoreCase(location))
            .toList();
    }

    @GetMapping("/{id}")
    public Job getJob(@PathVariable long id) {
        return jobs.stream().filter(j -> j.id() == id).findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job not found"));
    }
}
