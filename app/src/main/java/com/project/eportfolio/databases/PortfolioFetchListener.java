package com.project.eportfolio.databases;

import com.project.eportfolio.model.portfolio.TrPortofolio;

import java.util.List;

public interface PortfolioFetchListener {

    void onDeliverAllPortfolio(List<TrPortofolio> portofolio);

    void onDeliverPortfolio(TrPortofolio portofolio);

    void onHideDialog();
}
