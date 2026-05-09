package com.robotmonitor.flight.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@Profile("local")
public class LocalPassengerServiceImpl extends PassengerDevServiceImpl {
}
