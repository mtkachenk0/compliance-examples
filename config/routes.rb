Rails.application.routes.draw do
  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
  #
  default_url_options host: Rails.application.credentials[:default][:url]

  devise_for :users, controllers: {
    sessions: "users/sessions"
  }

  root to: "dashboards#index"
  namespace :api do
    namespace :priora do
      namespace :v2 do
        resources :tokens, only: [:create] do
          patch :revoke, on: :collection
        end
      end
    end
  end
end
