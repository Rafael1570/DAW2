package org.iesalixar.daw2.rafaelvegalibrero.ticketlogger.web;

import java.util.Set;
import org.springframework.ui.Model;

/** Normaliza los parámetros antes de calcular offsets o consultar JPA. */
public record Pagination(int page, int size, long totalElements, int totalPages,
                         String sortField, String sortDir) {
    public static Pagination of(int page, int size, long total, String field, String direction, Set<String> allowed) {
        int safeSize = Math.max(1, Math.min(size, 100));
        int pages = (int) Math.min(Integer.MAX_VALUE, (total + safeSize - 1) / safeSize);
        int lastPage = Math.min(Math.max(0, pages - 1), Integer.MAX_VALUE / safeSize);
        int safePage = Math.max(0, Math.min(page, lastPage));
        return new Pagination(safePage, safeSize, total, pages,
                allowed.contains(field) ? field : "name", "desc".equalsIgnoreCase(direction) ? "desc" : "asc");
    }

    public int offset() { return page * size; }

    public void addTo(Model model) {
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("offset", offset());
        model.addAttribute("totalElements", totalElements);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", "asc".equals(sortDir) ? "desc" : "asc");
    }
}
