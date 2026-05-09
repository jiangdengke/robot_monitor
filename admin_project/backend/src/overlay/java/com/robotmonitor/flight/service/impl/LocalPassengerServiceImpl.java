package com.robotmonitor.flight.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("local")
public class LocalPassengerServiceImpl extends PassengerDevServiceImpl {
}
