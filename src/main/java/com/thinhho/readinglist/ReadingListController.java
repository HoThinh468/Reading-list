package com.thinhho.readinglist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/") // to map all of its handler methods to a base URL path of “/”
public class ReadingListController {

    private final ReadingListRepository readingListRepository;

    @Autowired
    public ReadingListController(ReadingListRepository readingListRepository) {
        this.readingListRepository = readingListRepository;
    }

    /**
     * Handle HTTP GET request for /{reader} which returns a book list
     * for the reader specified in the path. It puts the list of Book into the model
     * under the key “books” and returns “readingList” as the logical name of the view
     * to render the mode
     */
    @RequestMapping(value = "/{reader}", method = RequestMethod.GET)
    public String readersBooks(@PathVariable("reader") String reader, Model model) {
        List<Book> readingList = readingListRepository.findByReader(reader);
        if (readingList != null) {
            model.addAttribute("books", readingList);
        }
        return "readingList";
    }

    /**
     * Handles HTTP POST requests for /{reader}, binding the
     * data in the body of the request to a Book object. This method sets the Book
     * object’s reader property to the reader’s name, and then saves the modified
     * Book via the repository’s save() method
     */
    @RequestMapping(value = "/{reader}", method = RequestMethod.POST)
    public String addToReadingList(@PathVariable("reader") String reader, Book book) {
        book.setReader(reader);
        readingListRepository.save(book);
        return "redirect:/{reader}";
    }
}
