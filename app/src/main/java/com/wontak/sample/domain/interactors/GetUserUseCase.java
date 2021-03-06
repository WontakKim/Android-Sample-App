package com.wontak.sample.domain.interactors;

import com.wontak.sample.base.BaseUseCase;
import com.wontak.sample.domain.models.User;
import com.wontak.sample.domain.repositories.GithubRepository;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GetUserUseCase extends BaseUseCase<User, String> {

    private GithubRepository githubRepository;

    @Inject
    public GetUserUseCase(GithubRepository githubRepository) {
        super(Schedulers.io(), AndroidSchedulers.mainThread());

        this.githubRepository = githubRepository;
    }

    @Override
    protected Observable<User> buildUseCaseObservable(String username) {
        return githubRepository.getUser(username);
    }
}
