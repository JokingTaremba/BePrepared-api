package com.jokingwill.beprepared.service.impl;

import com.jokingwill.beprepared.exception.BadRequestException;
import com.jokingwill.beprepared.exception.EntityNotFoundException;
import com.jokingwill.beprepared.model.Citizen;
import com.jokingwill.beprepared.model.City;
import com.jokingwill.beprepared.model.enums.Role;
import com.jokingwill.beprepared.repository.CitizenRepository;
import com.jokingwill.beprepared.service.CitizenService;
import com.jokingwill.beprepared.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CitizenServiceImpl implements CitizenService {

    private final LocationService locationService;
    private final CitizenRepository citizenRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String createCitizen(Citizen citizen, Long cityId) {
        if(citizenRepository.existsByPhone(citizen.getPhone())){
            throw new BadRequestException("Já existe cidadão com esse número!");
        }
        City city = locationService.getCityById(cityId);
        citizen.setCity(city);
        citizen.setVerified(false);
        citizen.setRole(Role.USER);
        citizen.setOtp(generateOtp(6));
        var savedCitizen = citizenRepository.save(citizen);
        return "Cidadão criado com sucesso! O seu OTP é: " + savedCitizen.getOtp();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Citizen> getAllCitizens() {
        return citizenRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Citizen> getAllCitizensByCityId(Long cityId) {
        return citizenRepository.findAllByCityId(cityId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Citizen> getAllCitizensByProvinceId(Long provinceId) {
        return citizenRepository.findAllByCityProvinceId(provinceId);
    }

    @Override
    @Transactional(readOnly = true)
    public Citizen getCitizenById(Long id) {
        return citizenRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Cidadão não encontrado!"));
    }

    @Override
    @Transactional
    public String verifyAccount(String otp) {
        Citizen citizen = citizenRepository.findByOtp(otp).orElseThrow(()->
                new EntityNotFoundException("Cidadão não encontrado!"));
        citizen.setVerified(true);
        citizen.setOtp(null);
        citizenRepository.save(citizen);
        return "A tua conta foi verificada com sucesso!";
    }

    @Override
    @Transactional
    public String generateOTPForCitizen(String phone) {
        Citizen citizen = citizenRepository.findByPhone(phone).orElseThrow(()->
                new EntityNotFoundException("Cidadão não encontrado, não foi possivel gerar o seu OTP!"));
        citizen.setOtp(null);
        String otp = generateOtp(6);
        citizen.setOtp(passwordEncoder.encode(otp));
        return "O seu codigo de acesso e: " + otp;
    }

    private static String generateOtp(int length){
        String otp = "";
        int x;
        char[] chars = new char[length];

        for(int i=0; i < length; i++){
            Random random = new Random();
            x = random.nextInt(9);
            chars[i] = Integer.toString(x).toCharArray()[0];
        }
        otp = new String(chars);
        return otp.trim();
    }
}
