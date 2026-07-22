package cl.kuichi.kuichiweb.service;

import cl.kuichi.kuichiweb.model.Clinic;
import cl.kuichi.kuichiweb.model.Offer;
import cl.kuichi.kuichiweb.repository.ClinicRepository;
import cl.kuichi.kuichiweb.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicService {

    @Autowired
    private ClinicRepository clinicRepo;

    @Autowired
    private OfferRepository offerRepo;

    public List<Clinic> getAllClinics() {
        return clinicRepo.findAll();
    }

    public List<Offer> getAllOffers() {
        return offerRepo.findAll();
    }
}