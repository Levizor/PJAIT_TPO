package pja.edu.pl.tpo11.Services;

import org.springframework.stereotype.Service;
import pja.edu.pl.tpo11.DTOs.LinkRequestBodyDTO;
import pja.edu.pl.tpo11.DTOs.LinkResponseDTO;
import pja.edu.pl.tpo11.DTOs.LinkUpdateRequest;
import pja.edu.pl.tpo11.Exceptions.NotFoundExcepiton;
import pja.edu.pl.tpo11.Exceptions.WrongPasswordException;
import pja.edu.pl.tpo11.Models.RedirectLink;
import pja.edu.pl.tpo11.Repositories.RedirectLinkRepository;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class LinkService {
    
    private RedirectLinkRepository _repository;
    
    public LinkService(RedirectLinkRepository repository){
        this._repository = repository;
    }
    
    public LinkResponseDTO createLink(LinkRequestBodyDTO linkDTO){
       RedirectLink link = new RedirectLink();
       link.setId(generateId());
       link.setName(linkDTO.getName());
       link.setTargetUrl(linkDTO.getTargetUrl());
       link.setPassword((linkDTO.getPassword().isBlank() || linkDTO.getPassword() == null) ? null: linkDTO.getPassword());
       link.setVisits(0);
       
       _repository.save(link);

       return LinkResponseDTO.fromLink(link);
    }
    
    private String generateId(){
        String id;
        do {

            id = new Random()
                    .ints(10, 0, 52)
                    .mapToObj(i -> i < 26 ? "" + (char) ('A' + 1) : "" + (char) ('a' + i - 26))
                    .collect(Collectors.joining());
        } while (_repository.existsById(id));
        return id;
    }
    
    public Optional<RedirectLink> getLink(String linkId){
        return _repository.findById(linkId);
    }

    public boolean nameExists(String linkName){
        return _repository.existsByNameEquals(linkName);
    }

    public boolean urlExists(String url){
        return _repository.existsByTargetUrlEquals(url);
    }
    
    public Optional<RedirectLink> visitLink(String linkId){
        var link = getLink(linkId);
        if (link.isEmpty()) return link;
        
        link.get().incrementVisits();
        _repository.save(link.get());
        
        return link;
    }

    public void updateLinkFromView(String linkId, LinkUpdateRequest updateData) throws NotFoundExcepiton {
        var linkOpt = _repository.findById(linkId);
        if (linkOpt.isEmpty()) throw new NotFoundExcepiton();

        var link = linkOpt.get();

        if (updateData.getName() != null)
            link.setName(updateData.getName());

        if (updateData.getTargetUrl() != null)
            link.setTargetUrl(updateData.getTargetUrl());

        _repository.save(link);
    }

    public void deleteLinkFromView(String linkId) throws NotFoundExcepiton {
        var linkOpt = _repository.findById(linkId);
        if (linkOpt.isEmpty()) throw new NotFoundExcepiton();

        _repository.delete(linkOpt.get());
    }

    public void updateLinkApi(String linkId, LinkRequestBodyDTO updLink) throws NotFoundExcepiton, WrongPasswordException {
        var linkOpt = _repository.findById(linkId);
        if (linkOpt.isEmpty()) throw new NotFoundExcepiton();
        
        var link = linkOpt.get();
        
        if(link.getPassword() == null) throw new WrongPasswordException();
        
        if(link.getPassword().equals(updLink.getPassword())) {
            if(updLink.getName() != null) 
                link.setName(updLink.getName());
            
            if(updLink.getTargetUrl() != null)
                link.setTargetUrl(updLink.getTargetUrl());
                
            _repository.save(link);
        }else 
            throw new WrongPasswordException();
            
    }
    
    public void deleteLink(String linkId, String password) throws WrongPasswordException {
        var linkOpt = _repository.findById(linkId);
        if(linkOpt.isEmpty()) return;
        
        var link = linkOpt.get();
        
        if(link.getPassword() == null) throw new WrongPasswordException();
        
        if(!link.getPassword().equals(password)) throw new WrongPasswordException();
        
        _repository.delete(link);
    }
    
}
