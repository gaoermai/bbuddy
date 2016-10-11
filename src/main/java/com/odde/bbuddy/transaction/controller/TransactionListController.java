package com.odde.bbuddy.transaction.controller;

import com.odde.bbuddy.common.controller.ResultRangeFactory;
import com.odde.bbuddy.transaction.domain.Transactions;
import com.odde.bbuddy.transaction.view.PresentableSummaryOfTransactions;
import com.odde.bbuddy.transaction.view.PresentableTransactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import static com.odde.bbuddy.common.controller.Urls.TRANSACTION;
import static com.odde.bbuddy.common.view.ModelAndViewCombiner.combine;

@Controller
@RequestMapping(TRANSACTION)
public class TransactionListController {
    private final Transactions transactions;
    private final PresentableTransactions presentableTransactions;
    private final PresentableSummaryOfTransactions presentableSummaryOfTransactions;
    private final ResultRangeFactory resultRangeFactory;

    @Autowired
    public TransactionListController(Transactions transactions, PresentableTransactions presentableTransactions, PresentableSummaryOfTransactions presentableSummaryOfTransactions, ResultRangeFactory resultRangeFactory) {
        this.transactions = transactions;
        this.presentableTransactions = presentableTransactions;
        this.presentableSummaryOfTransactions = presentableSummaryOfTransactions;
        this.resultRangeFactory = resultRangeFactory;
    }

    @GetMapping
    public ModelAndView index(@RequestParam(defaultValue = "1") int page) {
        transactions.processAll(presentableTransactions::display, resultRangeFactory.create(page))
                .withSummary(presentableSummaryOfTransactions::display);
        return combine(presentableTransactions).with(presentableSummaryOfTransactions);
    }

}
