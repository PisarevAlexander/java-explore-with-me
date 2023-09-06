package ru.practicum.main_server.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.main_server.compilation.CompilationRepository;
import ru.practicum.main_server.compilation.model.Compilation;
import ru.practicum.main_server.compilation.model.CompilationDto;
import ru.practicum.main_server.compilation.model.CompilationMapper;
import ru.practicum.main_server.event.EventRepository;
import ru.practicum.main_server.event.model.Event;
import ru.practicum.main_server.exception.NotFoundException;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Transactional
    @Override
    public Compilation create(CompilationDto compilationDto) {
        Compilation compilation = CompilationMapper.toCompilation(compilationDto);
        compilation.setEvents(eventRepository.findAllById(compilationDto.getEvents()));
        return compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public void delete(Long compId) {
        compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
        compilationRepository.deleteById(compId);
    }

    @Transactional
    @Override
    public Compilation update(Long compId, CompilationDto compilationDto) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
        if (compilationDto.getEvents() != null) {
            List<Event> events = eventRepository.findAllById(compilationDto.getEvents());
            compilation.setEvents(events);
        }
        if (compilationDto.getTitle() != null) {
            compilation.setPinned(compilationDto.getPinned());
        }
        if (compilationDto.getPinned() != null) {
            compilation.setPinned(compilationDto.getPinned());
        }
        return compilationRepository.save(compilation);
    }

    @Transactional
    @Override
    public Collection<Compilation> findAll(Boolean pinned, Pageable pageable) {
        if (pinned != null) {
            return compilationRepository.findAllByPinned(pinned, pageable).getContent();
        } else {
            return compilationRepository.findAll(pageable).getContent();
        }
    }

    @Transactional
    @Override
    public Compilation findById(Long compId) {
        return compilationRepository.findById(compId)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + compId + " not found"));
    }
}