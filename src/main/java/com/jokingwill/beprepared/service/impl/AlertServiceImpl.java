package com.jokingwill.beprepared.service.impl;

import com.jokingwill.beprepared.exception.EntityNotFoundException;
import com.jokingwill.beprepared.model.Alert;
import com.jokingwill.beprepared.model.City;
import com.jokingwill.beprepared.model.Province;
import com.jokingwill.beprepared.repository.AlertRepository;
import com.jokingwill.beprepared.service.AlertService;
import com.jokingwill.beprepared.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

    private final AlertRepository alertRepository;
    private final LocationService locationService;

    @Override
    @Transactional
    public String createAlert(Alert alert, Long cityId, Long provinceId) {
        City city = locationService.getCityById(cityId);
        Province province = locationService.getProvinceById(provinceId);
        alert.setActive(false);
        alert.setCity(city);
        alert.setProvince(province);
        alertRepository.save(alert);
        return "Alerta criado com sucesso!";
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllActiveAlerts() {
        return alertRepository.findAllByActive(true);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllAlertsByCityId(Long cityId) {
        return alertRepository.findAllByActiveAndCityId(true, cityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alert> getAllAlertsByProvinceId(Long provinceId) {
        return alertRepository.findAllByActiveAndProvinceId(true, provinceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Alert getAlertById(Long alertId) {
        return alertRepository.findById(alertId).orElseThrow(() -> new EntityNotFoundException("Alerta não encontrado!"));
    }

    @Override
    @Transactional
    public String activeAlert(Long alertId) {
        Alert alert = getAlertById(alertId);
        alert.setActive(true);
        alertRepository.save(alert);
        return "Perigo, proteja-se!";
    }
}
